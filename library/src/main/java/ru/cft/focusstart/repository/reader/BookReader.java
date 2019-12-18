package ru.cft.focusstart.repository.reader;

import ru.cft.focusstart.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class BookReader {

    private BookReader() {
    }

    public static Book readBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("book_id"));
        book.setName(rs.getString("book_name"));
        book.setDescription(rs.getString("book_description"));
        book.setIsbn(rs.getString("book_isbn"));

        return book;
    }
}
