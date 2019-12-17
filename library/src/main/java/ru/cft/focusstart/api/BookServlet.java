package ru.cft.focusstart.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.cft.focusstart.api.dto.BookDto;
import ru.cft.focusstart.service.book.BookService;
import ru.cft.focusstart.service.book.DefaultBookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.cft.focusstart.api.PathParser.getPathPart;

@WebServlet(urlPatterns = "/books/*")
public class BookServlet extends HttpServlet {

    private static final String BOOKS_PATTERN = "^/books";
    private static final String BOOK_PATTERN = "^/books/(?<id>[0-9]+)$";

    private final ObjectMapper mapper = new ObjectMapper();

    private final BookService bookService = DefaultBookService.getInstance();

    private final ExceptionHandler exceptionHandler = ExceptionHandler.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String uri = req.getServletPath();
            if (uri.matches(BOOKS_PATTERN)) {
                get(req, resp);
            } else if (uri.matches(BOOK_PATTERN)) {
                getById(req, resp);
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
            String uri = req.getServletPath();
            if (uri.matches(BOOKS_PATTERN)) {
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
            String uri = req.getServletPath();
            if (uri.matches(BOOK_PATTERN)) {
                merge(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String uri = req.getServletPath();
            if (uri.matches(BOOK_PATTERN)) {
                delete(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            exceptionHandler.handleExceptions(e, resp);
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BookDto request = mapper.readValue(req.getInputStream(), BookDto.class);

        BookDto response = bookService.create(request);
        writeResp(resp, response);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getPathPart(req.getServletPath(), BOOK_PATTERN, "id");

        BookDto response = bookService.getById(id);
        writeResp(resp, response);
    }

    private void get(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String authorName = req.getParameter("authorName");

        List<BookDto> response = bookService.get(name, authorName);
        writeResp(resp, response);
    }

    private void merge(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getPathPart(req.getServletPath(), BOOK_PATTERN, "id");
        BookDto request = mapper.readValue(req.getInputStream(), BookDto.class);

        BookDto response = bookService.merge(id, request);
        writeResp(resp, response);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = getPathPart(req.getServletPath(), BOOK_PATTERN, "id");

        bookService.delete(id);
    }

    private void writeResp(HttpServletResponse resp, Object response) throws IOException {
        resp.setContentType("application/json");
        mapper.writeValue(resp.getOutputStream(), response);
    }
}
