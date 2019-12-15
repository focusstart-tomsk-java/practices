package ru.cft.focusstart.mapper;

import ru.cft.focusstart.api.dto.BookDto;
import ru.cft.focusstart.entity.Book;

public class BookMapper {

    private static final BookMapper INSTANCE = new BookMapper();

    private BookMapper() {
    }

    public static BookMapper getInstance() {
        return INSTANCE;
    }

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .authorId(book.getAuthor().getId())
                .build();
    }
}
