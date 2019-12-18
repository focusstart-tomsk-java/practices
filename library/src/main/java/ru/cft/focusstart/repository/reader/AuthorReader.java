package ru.cft.focusstart.repository.reader;

import ru.cft.focusstart.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class AuthorReader {

    private AuthorReader() {
    }

    public static Author readAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong("author_id"));
        author.setName(rs.getString("author_name"));
        author.setDescription(rs.getString("author_description"));

        return author;
    }
}
