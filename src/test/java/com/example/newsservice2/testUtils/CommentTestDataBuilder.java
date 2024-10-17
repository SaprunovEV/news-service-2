package com.example.newsservice2.testUtils;

import com.example.newsservice2.news.model.CommentEntity;
import com.example.newsservice2.news.model.NewsEntity;

public class CommentTestDataBuilder implements TestDataBuilder<CommentEntity> {
    private TestDataBuilder<NewsEntity> news = NewsEntity::new;

    private CommentTestDataBuilder() {
    }

    private CommentTestDataBuilder(TestDataBuilder<NewsEntity> news) {
        this.news = news;
    }

    public static CommentTestDataBuilder aComment() {
        return new CommentTestDataBuilder();
    }

    public CommentTestDataBuilder withNews(TestDataBuilder<NewsEntity> news) {
        return new CommentTestDataBuilder(news);
    }

    @Override
    public CommentEntity build() {
        CommentEntity result = new CommentEntity();
        NewsEntity newsEntity = news.build();
        newsEntity.getComments().add(result);
        result.setNews(newsEntity);
        return result;
    }
}
