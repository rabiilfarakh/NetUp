package com.example.NetUp.comment.service;

import com.example.NetUp.comment.dtos.CommentDTOReq;
import com.example.NetUp.comment.dtos.CommentDTORes;

import java.util.List;

public interface CommentService {
    CommentDTORes createComment(CommentDTOReq commentDTOReq);
    CommentDTORes getCommentById(Long id);
    List<CommentDTORes> getAllComments();
    CommentDTORes updateComment(Long id, CommentDTOReq commentDTOReq);
    void deleteComment(Long id);
}
