package ru.otus.homework.popov.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private final EntityManager em;

    public CommentDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public void deleteById(long id) {
        var query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public long getCountByBook(Book book) {
        var query = em.createQuery("select count(c) from Comment c where id_book = :id_book", Long.class);
        query.setParameter("id_book", book.getId());
        return query.getSingleResult();

    }
}
