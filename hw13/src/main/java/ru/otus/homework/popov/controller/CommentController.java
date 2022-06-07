package ru.otus.homework.popov.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.User;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.repository.BookRepository;
import ru.otus.homework.popov.repository.CommentRepository;

@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    public CommentController(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @PostMapping("/api/comments")
    CommentDto addComment(@RequestBody CommentDto commentDto) {
        var book = bookRepository.findById(commentDto.getBookId());
        var newCommentDto = book.map(
                b -> {
                    var user = (User) SecurityContextHolder
                            .getContext().getAuthentication().getPrincipal();
                    var c = new Comment(null, commentDto.getText(), user.getFullName());
                    b.addComment(c);
                    c = commentRepository.save(c);
                    bookRepository.save(b);
                    return CommentDto.fromDomainObject(c);
                }
        );
        return newCommentDto.orElseThrow(() -> new NotFoundException());
    }

    @DeleteMapping("/api/comments/{id}")
    void deleteComment(@PathVariable String id) {
        commentRepository.deleteById(id);
    }


}
