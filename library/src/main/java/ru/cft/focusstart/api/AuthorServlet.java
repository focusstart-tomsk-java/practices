package ru.cft.focusstart.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.api.dto.AuthorDto;
import ru.cft.focusstart.api.dto.BookDto;
import ru.cft.focusstart.service.author.AuthorService;
import ru.cft.focusstart.service.author.DefaultAuthorService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.cft.focusstart.api.PathParser.getPathPart;

@WebServlet(urlPatterns = "/authors/*")
public class AuthorServlet extends HttpServlet {

    private static final String AUTHORS_PATTERN = "^/authors$";
    private static final String AUTHOR_PATTERN = "^/authors/(?<id>[0-9]+)$";
    private static final String AUTHOR_BOOKS_PATTERN = "^/authors/(?<id>[0-9]+)/books$";

    private final ObjectMapper mapper = new ObjectMapper();

    private final AuthorService authorService = DefaultAuthorService.getInstance();

    private final ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String path = getPath(req);
            if (path.matches(AUTHORS_PATTERN)) {
                get(req, resp);
            } else if (path.matches(AUTHOR_PATTERN)) {
                getById(req, resp);
            } else if (path.matches(AUTHOR_BOOKS_PATTERN)) {
                getBooks(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String path = getPath(req);
            if (path.matches(AUTHORS_PATTERN)) {
                create(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String path = getPath(req);
            if (path.matches(AUTHOR_PATTERN)) {
                merge(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AuthorDto request = mapper.readValue(req.getInputStream(), AuthorDto.class);

        AuthorDto response = authorService.create(request);
        writeResp(resp, response);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getPathPart(getPath(req), AUTHOR_PATTERN, "id");

        AuthorDto response = authorService.getById(id);
        writeResp(resp, response);
    }

    private void get(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");

        List<AuthorDto> response = authorService.get(name);
        writeResp(resp, response);
    }

    private void getBooks(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getPathPart(getPath(req), AUTHOR_BOOKS_PATTERN, "id");

        List<BookDto> response = authorService.getBooks(id);
        writeResp(resp, response);
    }

    private void merge(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getPathPart(getPath(req), AUTHOR_PATTERN, "id");
        AuthorDto request = mapper.readValue(req.getInputStream(), AuthorDto.class);

        AuthorDto response = authorService.merge(id, request);
        writeResp(resp, response);
    }

    private String getPath(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length());
    }

    private void writeResp(HttpServletResponse resp, Object response) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), response);
    }
}
