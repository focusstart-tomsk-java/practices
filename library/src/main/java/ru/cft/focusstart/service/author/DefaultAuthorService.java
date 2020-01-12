package ru.cft.focusstart.service.author;

import org.springframework.stereotype.Service;
import ru.cft.focusstart.api.dto.AuthorDto;
import ru.cft.focusstart.api.dto.BookDto;
import ru.cft.focusstart.entity.Author;
import ru.cft.focusstart.exception.ObjectNotFoundException;
import ru.cft.focusstart.mapper.AuthorMapper;
import ru.cft.focusstart.mapper.BookMapper;
import ru.cft.focusstart.repository.author.AuthorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.cft.focusstart.service.validation.Validator.*;

@Service
public class DefaultAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    private final BookMapper bookMapper;

    public DefaultAuthorService(
            AuthorRepository authorRepository,
            AuthorMapper authorMapper,
            BookMapper bookMapper
    ) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        validate(authorDto);

        Author author = add(null, authorDto);

        return authorMapper.toDto(author);
    }

    @Override
    public AuthorDto getById(Long id) {
        checkNotNull("id", id);

        Author author = getAuthor(id);

        return authorMapper.toDto(author);
    }

    @Override
    public List<AuthorDto> get(String name) {
        return authorRepository.get(name)
                .stream()
                .map(authorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooks(Long id) {
        checkNotNull("id", id);

        return Optional.ofNullable(getAuthor(id).getBooks())
                .orElse(Collections.emptyList())
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto merge(Long id, AuthorDto authorDto) {
        checkNotNull("id", id);
        validate(authorDto);

        Author author = authorRepository.getById(id)
                .map(existing -> update(existing, authorDto))
                .orElseGet(() -> add(id, authorDto));

        return authorMapper.toDto(author);
    }

    private void validate(AuthorDto authorDto) {
        checkNull("author.id", authorDto.getId());
        checkSize("author.name", authorDto.getName(), 1, 256);
        checkSize("author.description", authorDto.getDescription(), 1, 4096);
    }

    private Author add(Long id, AuthorDto authorDto) {
        Author author = new Author();
        author.setId(id);
        author.setName(authorDto.getName());
        author.setDescription(authorDto.getDescription());

        authorRepository.add(author);

        return author;
    }

    private Author getAuthor(Long id) {
        return authorRepository.getById(id)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Author with id %s not found", id)));
    }

    private Author update(Author author, AuthorDto authorDto) {
        author.setName(authorDto.getName());
        author.setDescription(authorDto.getDescription());

        authorRepository.update(author);

        return author;
    }
}
