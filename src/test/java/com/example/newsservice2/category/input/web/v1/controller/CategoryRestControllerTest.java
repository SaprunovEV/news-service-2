package com.example.newsservice2.category.input.web.v1.controller;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.testUtils.StringTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@AutoConfigureMockMvc
@Testcontainers
class CategoryRestControllerTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    public static Stream<Arguments> getPagination() {
        CategoryFilter f1 = new CategoryFilter();
        f1.setPageNumber(0);
        f1.setPageSize(2);
        CategoryFilter f2 = new CategoryFilter();
        f2.setPageNumber(1);
        f2.setPageSize(3);
        return Stream.of(
                Arguments.of(f1),
                Arguments.of(f2)
        );
    }

    @ParameterizedTest
    @MethodSource("getPagination")
    void whenGetAll_ThenReturnPageSizeAndPageNumber(CategoryFilter filter) throws Exception {
        String actual = mvc.perform(
                        get("/api/v1/category")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/responses/web/v1/find_by_filter_"+ filter.getPageNumber() +"_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("books[0].id", "books[0].categoryId"));
    }
}