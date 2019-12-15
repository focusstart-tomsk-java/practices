package ru.cft.focusstart.service.book;

import ru.cft.focusstart.api.dto.BookDto;
import ru.cft.focusstart.entity.Author;
import ru.cft.focusstart.entity.Book;
import ru.cft.focusstart.exception.InvalidParametersException;
import ru.cft.focusstart.exception.ObjectNotFoundException;
import ru.cft.focusstart.repository.author.AuthorRepository;
import ru.cft.focusstart.repository.author.InMemoryAuthorRepository;
import ru.cft.focusstart.repository.book.BookRepository;
import ru.cft.focusstart.repository.book.InMemoryBookRepository;
import ru.cft.focusstart.mapper.BookMapper;

import java.util.List;
import java.util.stream.Collectors;

import static ru.cft.focusstart.service.validation.Validator.*;

public class DefaultBookService implements BookService {

    private static final DefaultBookService INSTANCE = new DefaultBookService();

    private final AuthorRepository authorRepository = InMemoryAuthorRepository.getInstance();

    private final BookRepository bookRepository = InMemoryBookRepository.getInstance();

    private final BookMapper bookMapper = BookMapper.getInstance();

    private DefaultBookService() {
    }

    public static BookService getInstance() {
        return INSTANCE;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        validate(bookDto);

        Book book = add(null, bookDto);

        return bookMapper.toDto(book);
    }

    @Override
    public BookDto getById(Long id) {
        checkNotNull("id", id);

        Book book = getBook(id);

        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDto> get(String name, String authorName) {
        return bookRepository.get(name, authorName)
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto merge(Long id, BookDto bookDto) {
        checkNotNull("id", id);
        validate(bookDto);

        Book book = bookRepository.getById(id)
                .map(existing -> update(existing, bookDto))
                .orElse(add(id, bookDto));

        return bookMapper.toDto(book);
    }

    @Override
    public void delete(Long id) {
        checkNotNull("id", id);

        Book book = getBook(id);

        bookRepository.delete(book);
    }

    private void validate(BookDto bookDto) {
        checkNull("book.id", bookDto.getId());
        checkSize("book.name", bookDto.getName(), 1, 256);
        checkSize("book.description", bookDto.getDescription(), 1, 4096);
        checkSize("book.isbn", bookDto.getIsbn(), 1, 64);
        checkNotNull("book.authorId", bookDto.getAuthorId());
    }

    private Book add(Long id, BookDto bookDto) {
        Author author = getAuthor(bookDto.getAuthorId());

        Book book = new Book();
        book.setId(id);
        book.setName(bookDto.getName());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        book.setAuthor(author);

        author.getBooks().add(book);

        bookRepository.add(book);

        return book;
    }

    private Author getAuthor(Long id) {
        return authorRepository.getById(id)
                .orElseThrow(() -> new InvalidParametersException(String.format("Author with id %s not found", id)));
    }

    private Book getBook(Long id) {
        return bookRepository.getById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Book with id %s not found", id)));
    }

    private Book update(Book book, BookDto bookDto) {
        book.setName(bookDto.getName());
        book.setDescription(bookDto.getDescription());
        book.setIsbn(bookDto.getIsbn());
        if (!bookDto.getAuthorId().equals(book.getAuthor().getId())) {
            Author newAuthor = getAuthor(bookDto.getAuthorId());
            Author oldAuthor = book.getAuthor();

            oldAuthor.getBooks().remove(book);
            book.setAuthor(newAuthor);
            newAuthor.getBooks().add(book);
        }

        bookRepository.update(book);

        return book;
    }
}
