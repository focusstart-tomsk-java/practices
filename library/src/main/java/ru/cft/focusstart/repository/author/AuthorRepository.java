package ru.cft.focusstart.repository.author;

import ru.cft.focusstart.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    void add(Author author);

    Optional<Author> getById(Long id);

    List<Author> get(String name);
}
