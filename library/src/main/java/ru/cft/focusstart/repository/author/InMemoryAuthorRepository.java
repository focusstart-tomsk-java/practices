package ru.cft.focusstart.repository.author;

import ru.cft.focusstart.entity.Author;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryAuthorRepository implements AuthorRepository {

    private static final InMemoryAuthorRepository INSTANCE = new InMemoryAuthorRepository();

    private final AtomicLong idCounter = new AtomicLong();

    private final Map<Long, Author> storage = new ConcurrentHashMap<>();

    private InMemoryAuthorRepository() {
    }

    public static AuthorRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(Author author) {
        if (author.getId() == null) {
            author.setId(idCounter.incrementAndGet());
        }
        storage.put(author.getId(), author);
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Author> get(String name) {
        return storage.values()
                .stream()
                .filter(author -> filterByName(author, name))
                .collect(Collectors.toList());
    }

    private boolean filterByName(Author author, String name) {
        return name == null || author.getName() != null && author.getName().toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public void update(Author author) {
        storage.put(author.getId(), author);
    }
}
