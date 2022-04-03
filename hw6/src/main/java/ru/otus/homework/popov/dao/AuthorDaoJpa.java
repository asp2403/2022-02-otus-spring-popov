package ru.otus.homework.popov.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Author> getAll() {
        var query =  em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }
}
