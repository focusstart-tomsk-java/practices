package ru.cft.focusstart.entity;

import lombok.Data;

@Data
public class Book {

    private Long id;

    private String name;

    private String description;

    private String isbn;

    private Author author;
}
