package ru.cft.focusstart.repository.book;

import ru.cft.focusstart.entity.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {

    private static final InMemoryBookRepository INSTANCE = new InMemoryBookRepository();

    private final AtomicLong idCounter = new AtomicLong();

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    private InMemoryBookRepository() {
    }

    public static BookRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void add(Book book) {
        if (book.getId() == null) {
            book.setId(idCounter.incrementAndGet());
        }
        storage.put(book.getId(), book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Book> get(String name, String authorName) {
        return storage.values()
                .stream()
                .filter(book -> filterByName(book, name))
                .filter(book -> filterByAuthorName(book, authorName))
                .collect(Collectors.toList());
    }

    private boolean filterByName(Book book, String name) {
        return name == null || book.getName().toLowerCase().contains(name.toLowerCase());
    }

    private boolean filterByAuthorName(Book book, String authorName) {
        return authorName == null || book.getAuthor().getName().toLowerCase().contains(authorName.toLowerCase());
    }

    @Override
    public void update(Book book) {
        storage.put(book.getId(), book);
    }

    @Override
    public void delete(Book book) {
        storage.remove(book.getId());
    }
}
