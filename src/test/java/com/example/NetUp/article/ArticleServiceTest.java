package com.example.NetUp.article;

import com.example.NetUp.article.dtos.ArticleDTOReq;
import com.example.NetUp.article.dtos.ArticleDTORes;
import com.example.NetUp.article.entities.Article;
import com.example.NetUp.article.mapper.ArticleMapper;
import com.example.NetUp.article.repository.ArticleRepository;
import com.example.NetUp.article.service.ArticleServiceImpl;
import com.example.NetUp.user.dtos.UserDTORes;
import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private ArticleMapper articleMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private ArticleDTOReq articleDTOReq;
    private ArticleDTORes articleDTORes;
    private Article article;
    private User user;
    private UserDTORes userDTORes;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        userDTORes = new UserDTORes(
                1L, "testUser", "test@example.com",
                LocalDate.of(2000, 1, 1), "123 Street", 5,
                "Paris", "photo.jpg", null, null
        );

        articleDTOReq = new ArticleDTOReq(
                "photo.jpg",
                "Article Title",
                "This is a valid description for the article",
                LocalDateTime.now().minusDays(1),
                1L
        );

        article = new Article();
        article.setId(1L);
        article.setPhoto("photo.jpg");
        article.setTitle("Article Title");
        article.setDescription("This is a valid description for the article");
        article.setDate(LocalDateTime.now().minusDays(1));
        article.setUser(user);

        articleDTORes = new ArticleDTORes(
                1L,
                "photo.jpg",
                "Article Title",
                "This is a valid description for the article",
                LocalDateTime.now().minusDays(1),
                userDTORes,
                null
        );
    }

    @Test
    void createArticle_Success() {
        when(articleMapper.toEntity(articleDTOReq)).thenReturn(article);
        when(articleRepository.save(article)).thenReturn(article);
        when(articleMapper.toDto(article)).thenReturn(articleDTORes);

        ArticleDTORes result = articleService.createArticle(articleDTOReq);

        assertNotNull(result);
        assertEquals("Article Title", result.title());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void getArticleById_Success() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleMapper.toDto(article)).thenReturn(articleDTORes);

        ArticleDTORes result = articleService.getArticleById(1L);

        assertNotNull(result);
        assertEquals("Article Title", result.title());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void getArticleById_NotFound() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            articleService.getArticleById(1L);
        });

        assertEquals("Article not found", exception.getMessage());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void getAllArticles_Success() {
        when(articleRepository.findAll()).thenReturn(List.of(article));
        when(articleMapper.toDto(article)).thenReturn(articleDTORes);

        List<ArticleDTORes> result = articleService.getAllArticles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Article Title", result.get(0).title());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void getAllArticles_EmptyList() {
        when(articleRepository.findAll()).thenReturn(List.of());

        List<ArticleDTORes> result = articleService.getAllArticles();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void updateArticle_Success() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        doNothing().when(articleMapper).updateArticleFromDto(articleDTOReq, article);
        when(articleRepository.save(article)).thenReturn(article);
        when(articleMapper.toDto(article)).thenReturn(articleDTORes);

        ArticleDTORes result = articleService.updateArticle(1L, articleDTOReq);

        assertNotNull(result);
        assertEquals("Article Title", result.title());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void updateArticle_NotFound() {
        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            articleService.updateArticle(1L, articleDTOReq);
        });

        assertEquals("Article not found", exception.getMessage());
        verify(articleRepository, never()).save(any());
    }


}