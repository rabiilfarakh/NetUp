package com.example.NetUp.article.mapper;

import com.example.NetUp.article.entities.Article;
import com.example.NetUp.article.dtos.ArticleDTOReq;
import com.example.NetUp.article.dtos.ArticleDTORes;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(source = "user.id", target = "user.id")
    ArticleDTORes toDto(Article article);

    @Mapping(source = "user_id", target = "user.id")
    Article toEntity(ArticleDTOReq articleDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArticleFromDto(ArticleDTOReq articleDTOReq, @MappingTarget Article article);
}