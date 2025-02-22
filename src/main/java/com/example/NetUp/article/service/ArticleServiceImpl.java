package com.example.NetUp.article.service;

import com.example.NetUp.article.entities.Article;
import com.example.NetUp.article.mapper.ArticleMapper;
import com.example.NetUp.article.repository.ArticleRepository;
import com.example.NetUp.article.dtos.ArticleDTOReq;
import com.example.NetUp.article.dtos.ArticleDTORes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public ArticleDTORes createArticle(ArticleDTOReq articleDTOReq) {
        Article article = articleMapper.toEntity(articleDTOReq);
        Article savedArticle = articleRepository.save(article);
        return articleMapper.toDto(savedArticle);
    }

    @Override
    public ArticleDTORes getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return articleMapper.toDto(article);
    }

    @Override
    public List<ArticleDTORes> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDTORes updateArticle(Long id, ArticleDTOReq articleDTOReq) {
        Article existingArticle = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        articleMapper.updateArticleFromDto(articleDTOReq, existingArticle);
        return articleMapper.toDto(articleRepository.save(existingArticle));
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}

