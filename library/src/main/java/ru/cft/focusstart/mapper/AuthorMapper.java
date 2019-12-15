package ru.cft.focusstart.mapper;

import ru.cft.focusstart.api.dto.AuthorDto;
import ru.cft.focusstart.entity.Author;

public class AuthorMapper {

    private static final AuthorMapper INSTANCE = new AuthorMapper();

    private AuthorMapper() {
    }

    public static AuthorMapper getInstance() {
        return INSTANCE;
    }

    public AuthorDto toDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .description(author.getDescription())
                .build();
    }
}
