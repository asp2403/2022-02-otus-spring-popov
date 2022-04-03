package ru.otus.homework.popov.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Book> getAll() {
        var query = em.createQuery("select b from Book b", Book.class);
        return query.getResultList();
    }

    @Override
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Book where id_book = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }
}
