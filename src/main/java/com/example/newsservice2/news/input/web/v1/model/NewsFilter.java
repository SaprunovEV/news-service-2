package com.example.newsservice2.news.input.web.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsFilter {
    private int pageNumber;
    private int pageSize;
}
