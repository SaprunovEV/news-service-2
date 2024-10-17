package com.example.newsservice2.news.output.repository;

import com.example.newsservice2.news.model.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {
}
