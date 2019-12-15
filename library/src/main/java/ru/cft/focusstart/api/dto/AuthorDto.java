package ru.cft.focusstart.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@JsonDeserialize(builder = AuthorDto.Builder.class)
public class AuthorDto {

    private final Long id;

    private final String name;

    private final String description;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private Long id;

        private String name;

        private String description;

        private Builder() {
        }

        private Builder(AuthorDto authorDto) {
            this.id = authorDto.id;
            this.name = authorDto.name;
            this.description = authorDto.description;
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

        public AuthorDto build() {
            return new AuthorDto(this.id, this.name, this.description);
        }
    }

    private AuthorDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto authorDto = (AuthorDto) o;
        return Objects.equals(id, authorDto.id) &&
                Objects.equals(name, authorDto.name) &&
                Objects.equals(description, authorDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "AuthorDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
