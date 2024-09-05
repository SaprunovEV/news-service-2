package com.example.newsservice2.news.input.web.v1.mapper;

import com.example.newsservice2.news.input.web.v1.model.NewsItem;
import com.example.newsservice2.news.input.web.v1.model.NewsPageResponse;
import com.example.newsservice2.news.model.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedSourcePolicy = IGNORE)
public interface NewsMapper {
    default NewsPageResponse listEntityToPageResponse(List<NewsEntity> pageNews) {
        List<NewsItem> newsItems = pageNews.stream().map(this::entityToNewsItem).toList();
        return new NewsPageResponse(newsItems.size(), newsItems);
    }

    @Mapping(source = "author.id", target = "author")
    NewsItem entityToNewsItem(NewsEntity newsEntity);
}
