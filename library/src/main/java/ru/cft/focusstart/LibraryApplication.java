package ru.cft.focusstart;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LibraryApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
