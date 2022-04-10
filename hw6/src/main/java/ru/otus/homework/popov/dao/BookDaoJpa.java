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
        var entityGraph = em.getEntityGraph("book-entity-graph");
        var query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Book where id_book = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book getById(long id) {

        return em.find(Book.class, id);
    }
}
