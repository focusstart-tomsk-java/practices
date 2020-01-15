package ru.cft.focusstart.repository.book;

import ru.cft.focusstart.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void add(Book book);

    Optional<Book> getById(Long id);

    List<Book> get(String name, String authorName);

    void delete(Book book);
}
