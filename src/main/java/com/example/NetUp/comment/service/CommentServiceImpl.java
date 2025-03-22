package com.example.NetUp.comment.service;

import com.example.NetUp.article.repository.ArticleRepository;
import com.example.NetUp.comment.dtos.CommentDTOReq;
import com.example.NetUp.comment.dtos.CommentDTORes;
import com.example.NetUp.comment.entities.Comment;
import com.example.NetUp.article.entities.Article;
import com.example.NetUp.comment.mapper.CommentMapper;
import com.example.NetUp.comment.repository.CommentRepository;
import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTORes createComment(CommentDTOReq commentDTOReq) {

        Comment comment = commentMapper.toEntity(commentDTOReq);

        Article article = articleRepository.findById(commentDTOReq.article_id())
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));
        comment.setArticle(article);

        comment.setDate(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDto(savedComment);
    }

    @Override
    public CommentDTORes getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        return commentMapper.toDto(comment);
    }

    @Override
    public List<CommentDTORes> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTORes updateComment(Long id, CommentDTOReq commentDTOReq) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        comment.setDescription(commentDTOReq.description());

        if (!comment.getArticle().getId().equals(commentDTOReq.article_id())) {
            Article newArticle = articleRepository.findById(commentDTOReq.article_id())
                    .orElseThrow(() -> new EntityNotFoundException("Article not found"));
            comment.setArticle(newArticle);
        }

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }
}