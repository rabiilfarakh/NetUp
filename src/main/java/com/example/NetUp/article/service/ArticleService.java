package com.example.NetUp.article.service;

import com.example.NetUp.article.dtos.ArticleDTOReq;
import com.example.NetUp.article.dtos.ArticleDTORes;

import java.util.List;

public interface ArticleService {
    ArticleDTORes createArticle(ArticleDTOReq articleDTOReq);
    ArticleDTORes getArticleById(Long id);
    List<ArticleDTORes> getAllArticles();
    ArticleDTORes updateArticle(Long id, ArticleDTOReq articleDTOReq);
    void deleteArticle(Long id);
}
