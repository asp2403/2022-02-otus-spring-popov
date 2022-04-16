package ru.otus.homework.popov.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.dto.BookDto;

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
    public List<BookDto> getDtoAll() {
        var query = em.createQuery("select new ru.otus.homework.popov.dto.BookDto(b.id, b.title, a.name, g.name, count(c)) " +
                "from Book b " +
                "left join b.author a " +
                "left join b.genre g " +
                "left join b.comments c " +
                "group by b.id order by b.id", BookDto.class);
        return query.getResultList();
    }

    @Override
    public BookDto getDtoById(long id) {
        var query = em.createQuery("select new ru.otus.homework.popov.dto.BookDto(b.id, b.title, a.name, g.name, count(c)) " +
                "from Book b " +
                "left join b.author a " +
                "left join b.genre g " +
                "left join b.comments c " +
                "where b.id = :id_book group by b.id", BookDto.class);
        query.setParameter("id_book", id);
        return query.getSingleResult();
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

    @Override
    public Book getWithDetailsById(long id) {
        var entityGraph = em.getEntityGraph("book-entity-graph");
        var query = em.createQuery("select b from Book b where b.id = :id_book", Book.class);
        query.setParameter("id_book", id);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getSingleResult();
    }

}
