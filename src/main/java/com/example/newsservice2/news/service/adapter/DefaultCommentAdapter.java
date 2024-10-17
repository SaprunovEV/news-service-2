package com.example.newsservice2.news.service.adapter;

import com.example.newsservice2.news.model.NewsEntity;

@Deprecated
public class DefaultCommentAdapter implements CommentAdapter {
    @Override
    public Long getCountByNews(NewsEntity news) {
        return null;
    }
}
