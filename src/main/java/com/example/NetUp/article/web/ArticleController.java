package com.example.NetUp.article.web;

import com.example.NetUp.article.dtos.ArticleDTOReq;
import com.example.NetUp.article.dtos.ArticleDTORes;
import com.example.NetUp.article.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }


    @PostMapping
    public ResponseEntity<ArticleDTORes> createArticle(@RequestBody ArticleDTOReq articleDTOReq) {
        ArticleDTORes createdArticle = articleService.createArticle(articleDTOReq);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTORes> getArticleById(@PathVariable Long id) {
        ArticleDTORes article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTORes>> getAllArticles() {
        List<ArticleDTORes> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArticleDTORes> patchArticle(
            @PathVariable Long id,
            @RequestBody ArticleDTOReq articleDTOReq
    ) {
        ArticleDTORes updatedArticle = articleService.updateArticle(id, articleDTOReq);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }

}

