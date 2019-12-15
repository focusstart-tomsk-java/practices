package ru.cft.focusstart.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = BookDto.Builder.class)
public class BookDto {

    private final Long id;

    private final String name;

    private final String description;

    private final String isbn;

    private final Long authorId;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Long id;

        private String name;

        private String description;

        private String isbn;

        private Long authorId;

        private Builder() {
        }

        private Builder(BookDto bookDto) {
            this.id = bookDto.id;
            this.name = bookDto.name;
            this.description = bookDto.description;
            this.isbn = bookDto.isbn;
            this.authorId = bookDto.authorId;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder authorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }

        public BookDto build() {
            return new BookDto(this.id, this.name, this.description, this.isbn, this.authorId);
        }
    }

    private BookDto(Long id, String name, String description, String isbn, Long authorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isbn = isbn;
        this.authorId = authorId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIsbn() {
        return isbn;
    }

    public Long getAuthorId() {
        return authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) &&
                Objects.equals(name, bookDto.name) &&
                Objects.equals(description, bookDto.description) &&
                Objects.equals(isbn, bookDto.isbn) &&
                Objects.equals(authorId, bookDto.authorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, isbn, authorId);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", authorId=" + authorId +
                '}';
    }
}
