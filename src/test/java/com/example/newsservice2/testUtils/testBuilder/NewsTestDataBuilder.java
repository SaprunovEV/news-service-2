package com.example.newsservice2.testUtils.testBuilder;

import com.example.newsservice2.news.model.CommentEntity;
import com.example.newsservice2.news.model.NewsEntity;
import com.example.newsservice2.testUtils.TestDataBuilder;

import java.util.ArrayList;
import java.util.List;

public class NewsTestDataBuilder implements TestDataBuilder<NewsEntity> {
    private Long author = 1L;
    private List<TestDataBuilder<CommentEntity>> comments = new ArrayList<>();
    private String topic = "News test topic";
    private String body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

    private NewsTestDataBuilder() {}

    private NewsTestDataBuilder(
            Long author,
            List<TestDataBuilder<CommentEntity>> comments,
            String topic,
            String body) {
        this.author = author;
        this.comments = comments;
        this.topic = topic;
        this.body = body;
    }

    public static NewsTestDataBuilder aNews() {
        return new NewsTestDataBuilder();
    }

    public NewsTestDataBuilder withAuthor(Long author) {
        return new NewsTestDataBuilder(author, comments, topic, body);
    }

    public NewsTestDataBuilder withComments(List<TestDataBuilder<CommentEntity>> comments) {
        return new NewsTestDataBuilder(author, comments, topic, body);
    }

    public NewsTestDataBuilder withTopic(String topic) {
        return new NewsTestDataBuilder(author, comments, topic, body);
    }

    public NewsTestDataBuilder withBody(String body) {
        return new NewsTestDataBuilder(author, comments, topic, body);
    }

    @Override
    public NewsEntity build() {
        NewsEntity result = new NewsEntity();

        result.setTopic(topic);
        result.setBody(body);
        result.setAuthor(author);
        result.setComment(getEntityCollection(comments, c -> c.setNews(result)));

        return result;
    }
}
