package ru.cft.focusstart.repository.author;

import org.springframework.stereotype.Repository;
import ru.cft.focusstart.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepository implements AuthorRepository {

    private static final String GET_BY_NAME_QUERY =
            "select a from Author a where lower(a.name) like lower(concat('%', :name, '%'))";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Author author) {
        entityManager.persist(author);
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Author.class, id));
    }

    @Override
    public List<Author> get(String name) {
        return entityManager.createQuery(GET_BY_NAME_QUERY, Author.class)
                .setParameter("name", name == null ? "" : name)
                .getResultList();
    }
}
