package com.example.newsservice2.news.input.web.v1.controller;

import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.news.service.adapter.AuthorAdapter;
import com.example.newsservice2.news.service.adapter.CategoryAdapter;
import com.example.newsservice2.news.service.adapter.CommentAdapter;
import com.example.newsservice2.testUtils.StringTestUtils;
import com.example.newsservice2.testUtils.TestDataBuilder;
import com.example.newsservice2.testUtils.testBuilder.UserTestDataBuilder;
import com.example.newsservice2.user.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.example.newsservice2.testUtils.testBuilder.NewsTestDataBuilder.aNews;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@ImportAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@Testcontainers
@MockBeans(value = {
        @MockBean(value = AuthorAdapter.class),
        @MockBean(value = CategoryAdapter.class),
        @MockBean(value = CommentAdapter.class)
})
class NewsRestControllerTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;


    @Test
    void whenGetAll_thenReturnPageOfNewsWithCountComments() throws Exception {
        TestDataBuilder<UserEntity> userId = getFacade().persistedOnce(UserTestDataBuilder.aUser());
        getFacade().save(aNews().withAuthor(userId).withBody("test1").withTopic("test1"));
        getFacade().save(aNews().withAuthor(userId).withBody("test2").withTopic("test2"));
        getFacade().save(aNews().withAuthor(userId).withBody("test3").withTopic("test3"));
        getFacade().save(aNews().withAuthor(userId).withBody("test4").withTopic("test4"));

        String actual = mvc.perform(
                        get("/api/v1/news")
                                .param("pageNumber", "0")
                                .param("pageSize", "3"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/response/news/web/v1/get_page_news_response.json");

        JsonAssert.assertJsonEquals(
                expected,
                actual,
                JsonAssert.whenIgnoringPaths("news[*].id", "news[*].author", "news[*].categories", "news[*].createAt", "news[*].updateAt"));
    }
}