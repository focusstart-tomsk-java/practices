package ru.cft.focusstart.repository.author;

import ru.cft.focusstart.entity.Author;
import ru.cft.focusstart.entity.Book;
import ru.cft.focusstart.repository.DataAccessException;
import ru.cft.focusstart.repository.DataSourceProvider;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static ru.cft.focusstart.repository.reader.AuthorReader.readAuthor;
import static ru.cft.focusstart.repository.reader.BookReader.readBook;

public class JdbcAuthorRepository implements AuthorRepository {

    private static final String ADD_QUERY =
            "insert into author (name, description) " +
            "values (?, ?)";

    private static final String GET_QUERY =
            "select a.id          author_id," +
            "       a.name        author_name," +
            "       a.description author_description," +
            "       b.id          book_id," +
            "       b.name        book_name," +
            "       b.description book_description," +
            "       b.isbn        book_isbn" +
            "     from author a " +
            "left join book   b on a.id = b.author_id";

    private static final String GET_BY_ID_QUERY =
            GET_QUERY +
            " where a.id = ?";

    private static final String GET_BY_NAME_QUERY =
            GET_QUERY +
            " where lower(a.name) like lower('%' || ? || '%')";

    private static final String UPDATE_QUERY =
            "update author " +
            "set name = ?, description = ? " +
            "where id = ?";

    private static final JdbcAuthorRepository INSTANCE = new JdbcAuthorRepository();

    private final DataSource dataSource;

    private JdbcAuthorRepository() {
        this.dataSource = DataSourceProvider.getDataSource();
    }

    public static AuthorRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(Author author) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(ADD_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getDescription());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            Long id = rs.next() ? rs.getLong(1) : null;
            if (id == null) {
                throw new SQLException("Unexpected error - could not obtain id");
            }
            author.setId(id);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<Author> getById(Long id) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_BY_ID_QUERY)
        ) {
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            Collection<Author> authors = readAuthorsList(rs);

            if (authors.isEmpty()) {
                return Optional.empty();
            } else if (authors.size() == 1) {
                return Optional.of(authors.iterator().next());
            } else {
                throw new SQLException("Unexpected result set size");
            }
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<Author> get(String name) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_BY_NAME_QUERY)
        ) {
            ps.setString(1, name == null ? "" : name);

            ResultSet rs = ps.executeQuery();

            Collection<Author> authors = readAuthorsList(rs);

            return new ArrayList<>(authors);
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void update(Author author) {
        try (
                Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)
        ) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getDescription());
            ps.setLong(3, author.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private Collection<Author> readAuthorsList(ResultSet rs) throws SQLException {
        Map<Long, Author> result = new HashMap<>();
        while (rs.next()) {
            long authorId = rs.getLong("author_id");

            Author author = result.get(authorId);
            if (author == null) {
                author = readAuthor(rs);
                result.put(authorId, author);
            }

            long bookId = rs.getLong("book_id");
            if (rs.wasNull()) {
                continue;
            }
            Book book = readBook(rs);
            book.setAuthor(author);
            if (author.getBooks() == null) {
                author.setBooks(new ArrayList<>());
            }
            author.getBooks().add(book);
        }
        return result.values();
    }
}
