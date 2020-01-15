package ru.cft.focusstart.repository.book;

import org.springframework.stereotype.Repository;
import ru.cft.focusstart.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BookRepository {

    private static final String GET_BY_NAME_QUERY =
            "select b " +
            "from Book b join fetch b.author " +
            "where lower(b.name) like lower(concat('%', :name, '%')) " +
            "  and lower(b.author.name) like lower(concat('%', :authorName, '%'))";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Book.class, id));
    }

    @Override
    public List<Book> get(String name, String authorName) {
        return entityManager.createQuery(GET_BY_NAME_QUERY, Book.class)
                .setParameter("name", name == null ? "" : name)
                .setParameter("authorName", authorName == null ? "" : authorName)
                .getResultList();
    }

    @Override
    public void delete(Book book) {
        entityManager.remove(book);
    }
}
