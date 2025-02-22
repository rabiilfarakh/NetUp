package com.example.NetUp.article.mapper;

import com.example.NetUp.article.entities.Article;
import com.example.NetUp.article.dtos.ArticleDTOReq;
import com.example.NetUp.article.dtos.ArticleDTORes;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    ArticleDTORes toDto(Article article);

    Article toEntity(ArticleDTOReq articleDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateArticleFromDto(ArticleDTOReq articleDTOReq, @MappingTarget Article article);
}