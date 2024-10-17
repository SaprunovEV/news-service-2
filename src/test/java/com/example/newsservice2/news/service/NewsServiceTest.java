package com.example.newsservice2.news.service;

import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.news.service.adapter.AuthorAdapter;
import com.example.newsservice2.news.service.adapter.CategoryAdapter;
import com.example.newsservice2.news.service.adapter.CommentAdapter;
import com.example.newsservice2.news.service.model.NewsManager;
import com.example.newsservice2.testUtils.TestDataBuilder;
import com.example.newsservice2.testUtils.testBuilder.UserTestDataBuilder;
import com.example.newsservice2.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.example.newsservice2.testUtils.testBuilder.NewsTestDataBuilder.aNews;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@ContextConfiguration(classes = NewsServiceConf.class)
class NewsServiceTest extends AbstractIntegrationTest {
    @Autowired
    private NewsService service;
    @Autowired
    private AuthorAdapter authorAdapter;
    @Autowired
    private CategoryAdapter categoryAdapter;
    @Autowired
    private CommentAdapter commentAdapter;

    @Test
    void whenGetPageNews_thenReturnPageOfUser() throws Exception {
        TestDataBuilder<UserEntity> userBuilder = getFacade().persistedOnce(UserTestDataBuilder.aUser());

        getFacade().save(aNews().withAuthor(userBuilder));
        getFacade().save(aNews().withAuthor(userBuilder));
        getFacade().save(aNews().withAuthor(userBuilder));
        getFacade().save(aNews().withAuthor(userBuilder));

        int pageNumber = 0;
        int pageSize = 2;

        List<NewsManager> actual = service.getPageNews(pageNumber, pageSize);

        assertEquals(2, actual.size());

        verify(authorAdapter, times(2)).getAuthorByNews(any());
        verify(categoryAdapter, times(2)).getCategoryByNews(any());
        verify(commentAdapter, times(2)).getCountByNews(any());
    }
}