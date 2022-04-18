package ru.otus.homework.popov.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.homework.popov.domain.Book;
import ru.otus.homework.popov.projection.BookCommentCountProjection;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select new ru.otus.homework.popov.projection.BookCommentCountProjection(b.id, b.title, a.name, g.name, count(c)) " +
            "from Book b " +
            "left join b.author a " +
            "left join b.genre g " +
            "left join b.comments c " +
            "group by b.id order by b.id")
    List<BookCommentCountProjection> findCommentCountProjectionAll();

    @Query("select new ru.otus.homework.popov.projection.BookCommentCountProjection(b.id, b.title, a.name, g.name, count(c)) " +
            "from Book b " +
            "left join b.author a " +
            "left join b.genre g " +
            "left join b.comments c " +
            "where b.id = :idBook " +
            "group by b.id")
    Optional<BookCommentCountProjection> findCommentCountProjectionById(@Param("idBook") long idBook);

    @EntityGraph(attributePaths = {"author", "genre"})
    @Query("select b from Book b where b.id = :idBook")
    Optional<Book> findWithDetailsById(@Param("idBook") long idBook);

}
