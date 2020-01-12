package ru.cft.focusstart.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
public class Author {

    private Long id;

    private String name;

    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Book> books;
}
