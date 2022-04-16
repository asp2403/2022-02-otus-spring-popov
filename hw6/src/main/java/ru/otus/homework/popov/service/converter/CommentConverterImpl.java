package ru.otus.homework.popov.service.converter;

import org.springframework.stereotype.Component;
import ru.otus.homework.popov.domain.Comment;

@Component
public class CommentConverterImpl implements CommentConverter {
    @Override
    public String convertToString(Comment comment) {
        return "<" + comment.getId() + "> " + comment.getText();
    }
}
