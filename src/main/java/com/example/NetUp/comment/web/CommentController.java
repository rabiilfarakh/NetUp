package com.example.NetUp.comment.web;

import com.example.NetUp.comment.dtos.CommentDTOReq;
import com.example.NetUp.comment.dtos.CommentDTORes;
import com.example.NetUp.comment.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTORes> createComment(@RequestBody CommentDTOReq commentDTOReq) {
        CommentDTORes commentDTORes = commentService.createComment(commentDTOReq);
        return ResponseEntity.ok(commentDTORes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTORes> getCommentById(@PathVariable Long id) {
        CommentDTORes commentDTORes = commentService.getCommentById(id);
        return ResponseEntity.ok(commentDTORes);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTORes>> getAllComments() {
        List<CommentDTORes> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTORes> updateComment(@PathVariable Long id, @RequestBody CommentDTOReq commentDTOReq) {
        CommentDTORes updatedComment = commentService.updateComment(id, commentDTOReq);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
