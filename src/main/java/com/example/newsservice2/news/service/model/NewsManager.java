package com.example.newsservice2.news.service.model;

import com.example.newsservice2.news.model.NewsEntity;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class NewsManager {
    public void persist() {
        this.news.persist();
    }

    public void update() {
        this.news.update();
    }

    public Long getId() {
        return this.news.getId();
    }

    public String getTopic() {
        return this.news.getTopic();
    }

    public String getBody() {
        return this.news.getBody();
    }

    public Instant getCreateAt() {
        return this.news.getCreateAt();
    }

    public Instant getUpdateAt() {
        return this.news.getUpdateAt();
    }

    public void setId(Long id) {
        this.news.setId(id);
    }

    public void setTopic(String topic) {
        this.news.setTopic(topic);
    }

    public void setBody(String body) {
        this.news.setBody(body);
    }

    public void setCreateAt(Instant createAt) {
        this.news.setCreateAt(createAt);
    }

    public void setUpdateAt(Instant updateAt) {
        this.news.setUpdateAt(updateAt);
    }

    private NewsEntity news;

    private Author author;
    private List<Long> categories;

    private Long count;
}
