package com.example.newsservice2.category.service;

import com.example.newsservice2.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ContextConfiguration(classes = CategoryServiceConf.class)
class CategoryServiceTest extends AbstractIntegrationTest {
    @Autowired
    private CategoryService service;

}