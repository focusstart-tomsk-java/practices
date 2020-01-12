package ru.cft.focusstart.repository.book;

import org.springframework.stereotype.Repository;
import ru.cft.focusstart.entity.Author;
import ru.cft.focusstart.entity.Book;
import ru.cft.focusstart.repository.DataAccessException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static ru.cft.focusstart.repository.reader.AuthorReader.readAuthor;
import static ru.cft.focusstart.repository.reader.BookReader.readBook;

@Repository
public class JdbcBookRepository implements BookRepository {

    private static final String ADD_QUERY =
            "insert into book (name, description, isbn, author_id) " +
            "values (?, ?, ?, ?)";

    private static final String GET_QUERY =
            "select b.id          book_id," +
            "       b.name        book_name," +
            "       b.description book_description," +
            "       b.isbn        book_isbn," +
            "       a.id          author_id," +
            "       a.name        author_name," +
            "       a.description author_description " +
            "from book b, author a " +
            "where b.author_id = a.id";

    private static final String GET_BY_ID_QUERY =
            GET_QUERY +
            " and b.id = ?";

    private static final String GET_BY_NAME_QUERY =
            GET_QUERY +
            " and lower(b.name) like lower('%' || ? || '%')" +
            " and lower(a.name) like lower('%' || ? || '%')";

    private static final String UPDATE_QUERY =
            "update book " +
            "set name = ?, description = ?, isbn = ?, author_id = ? " +
            "where id = ?";

    private static final String DELETE_QUERY =
            "delete from book where id = ?";

    private DataSource dataSource;

    public JdbcBookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Book book) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getDescription());
            ps.setString(3, book.getIsbn());
            ps.setLong(4, book.getAuthor().getId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            Long id = rs.next() ? rs.getLong(1) : null;
            if (id == null) {
                throw new SQLException("Unexpected error - could not obtain id");
            }
            book.setId(id);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<Book> getById(Long id) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_BY_ID_QUERY)
        ) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            Collection<Book> books = readBooksList(rs);

            if (books.isEmpty()) {
                return Optional.empty();
            } else if (books.size() == 1) {
                return Optional.of(books.iterator().next());
            } else {
                throw new SQLException("Unexpected result set size");
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Book> get(String name, String authorName) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_BY_NAME_QUERY)
        ) {
            ps.setString(1, name == null ? "" : name);
            ps.setString(2, authorName == null ? "" : authorName);

            ResultSet rs = ps.executeQuery();

            Collection<Book> books = readBooksList(rs);

            return new ArrayList<>(books);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void update(Book book) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)
        ) {
            ps.setString(1, book.getName());
            ps.setString(2, book.getDescription());
            ps.setString(3, book.getIsbn());
            ps.setLong(4, book.getAuthor().getId());
            ps.setLong(5, book.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void delete(Book book) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(DELETE_QUERY)
        ) {
            ps.setLong(1, book.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private Collection<Book> readBooksList(ResultSet rs) throws SQLException {
        List<Book> result = new ArrayList<>();
        while (rs.next()) {
            Book book = readBook(rs);

            Author author = readAuthor(rs);
            book.setAuthor(author);

            result.add(book);
        }
        return result;
    }
}
