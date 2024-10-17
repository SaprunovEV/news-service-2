package com.example.newsservice2.news.input.web.v1.mapper;

import com.example.newsservice2.news.input.web.v1.model.NewsItem;
import com.example.newsservice2.news.input.web.v1.model.NewsPageResponse;
import com.example.newsservice2.news.service.model.NewsManager;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedSourcePolicy = IGNORE)
public interface NewsMapper {
    default NewsPageResponse listEntityToPageResponse(List<NewsManager> pageNews) {
        List<NewsItem> newsItems = pageNews.stream().map(this::entityToNewsItem).toList();
        return new NewsPageResponse(newsItems.size(), newsItems);
    }
    NewsItem entityToNewsItem(NewsManager newsEntity);
}
