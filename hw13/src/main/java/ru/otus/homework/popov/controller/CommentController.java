package ru.otus.homework.popov.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.homework.popov.controller.dto.CommentDto;
import ru.otus.homework.popov.domain.Comment;
import ru.otus.homework.popov.domain.User;
import ru.otus.homework.popov.exception.NotFoundException;
import ru.otus.homework.popov.service.BookOperations;
import ru.otus.homework.popov.service.CommentOperations;

@RestController
public class CommentController {
    private final CommentOperations commentOperations;
    private final BookOperations bookOperations;

    public CommentController(CommentOperations commentOperations, BookOperations bookOperations) {
        this.commentOperations = commentOperations;
        this.bookOperations = bookOperations;
    }

    @PostMapping("/api/comments")
    CommentDto addComment(@RequestBody CommentDto commentDto) {
        var book = bookOperations.findById(commentDto.getBookId());
        var newCommentDto = book.map(
                b -> {
                    var user = (User) SecurityContextHolder
                            .getContext().getAuthentication().getPrincipal();
                    var c = new Comment(null, commentDto.getText(), user.getFullName());
                    c = commentOperations.addComment(b, c);
                    return CommentDto.fromDomainObject(c);
                }
        );
        return newCommentDto.orElseThrow(() -> new NotFoundException());
    }

    @DeleteMapping("/api/comments/{id}")
    void deleteComment(@PathVariable String id) {
        var comment = commentOperations.findById(id).orElseThrow(() -> new NotFoundException());
        commentOperations.delComment(comment);
    }


}
