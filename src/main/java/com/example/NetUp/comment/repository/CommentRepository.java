package com.example.NetUp.comment.repository;

import com.example.NetUp.comment.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
