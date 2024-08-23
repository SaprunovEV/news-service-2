package com.example.newsservice2.category.input.web.v1.controller;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryId;
import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import com.example.newsservice2.category.model.CategoryEntity;
import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.testUtils.CategoryPayloadTestDataBuilder;
import com.example.newsservice2.testUtils.StringTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static com.example.newsservice2.category.input.web.v1.controller.testBuilder.CategoryTestDataBuilder.aCategory;
import static com.example.newsservice2.testUtils.CategoryPayloadTestDataBuilder.aCategoryPayload;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@Transactional
@AutoConfigureCache
@ImportAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
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

    public static Stream<Arguments> getNegativePageNumber() {
        CategoryFilter f1 = new CategoryFilter();
        f1.setPageSize(1);
        f1.setPageNumber(-1);

        return Stream.of(
                Arguments.of(f1)
        );
    }

    public static Stream<Arguments> getNegativePageSize() {
        CategoryFilter f1 = new CategoryFilter();
        f1.setPageSize(-1);
        f1.setPageNumber(1);
        CategoryFilter f2 = new CategoryFilter();
        f2.setPageSize(0);
        f2.setPageNumber(1);
        return Stream.of(
                Arguments.of(f1),
                Arguments.of(f2)
        );
    }

    public static Stream<Arguments> payloadData() {
        CategoryPayload cp1 = new CategoryPayload();
        cp1.setName("   ");
        PayloadData p1 = new PayloadData(cp1, 1);

        CategoryPayload cp2 = new CategoryPayload();
        PayloadData p2 = new PayloadData(cp2, 2);

        CategoryPayload cp3 = new CategoryPayload();
        cp3.setName("qwer");
        PayloadData p3 = new PayloadData(cp3, 3);
        return Stream.of(
                Arguments.of(p1),
                Arguments.of(p2),
                Arguments.of(p3)
        );
    }

    public static Stream<Arguments> getCategoryId() {
        CategoryId id1 = new CategoryId();
        id1.setId(-1L);
        return Stream.of(
                Arguments.of(id1)
        );
    }

    @ParameterizedTest
    @MethodSource("getPagination")
    void whenGetAll_ThenReturnPageSizeAndPageNumber(CategoryFilter filter) throws Exception {
        getFacade().save(aCategory().withName("category 1"));
        getFacade().save(aCategory().withName("category 2"));
        getFacade().save(aCategory().withName("category 3"));
        getFacade().save(aCategory().withName("category 4"));
        getFacade().save(aCategory().withName("category 5"));

        String actual = mvc.perform(
                        get("/api/v1/category")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/response/category/web/v1/find_by_filter_" + filter.getPageNumber() + "_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("categories[*].id"));
    }

    @Test
    void whenCreateNewCategory_thenReturnCategoryWithId_andSaveCategoryToDatabase() throws Exception {
        CategoryPayloadTestDataBuilder testDataBuilder = aCategoryPayload();

        String actual = mvc.perform(
                        post("/api/v1/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(testDataBuilder.build())))
                .andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/response/category/web/v1/create_new_category_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("id"));
    }

    @Test
    void whenUpdateCategory_thenReturnCategoryWithNewName() throws Exception {
        CategoryPayloadTestDataBuilder payload = aCategoryPayload().withName("new_category_name");
        CategoryEntity categoryToDatabase = getFacade().save(aCategory());

        String actual = mvc.perform(
                        put("/api/v1/category/{id}", categoryToDatabase.getId())
                                .content(objectMapper.writeValueAsString(payload.build()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/response/category/web/v1/update_category_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("id"));

        assertAll(() -> {
            CategoryEntity entity = getFacade().find(categoryToDatabase.getId(), CategoryEntity.class);
            assertNotNull(entity);
            assertEquals(entity.getName(), payload.build().getName());
        });
    }


    @Test
    void whenDelete_thenReturnNoContent_andDeleteCategoryFromDatabase() throws Exception {
        CategoryEntity categoryToDatabase = getFacade().save(aCategory());

        mvc.perform(delete("/api/v1/category/{id}", categoryToDatabase.getId()))
                .andExpect(status().isNoContent());

        assertNull(getFacade().find(categoryToDatabase.getId(), CategoryEntity.class));
    }

    @ParameterizedTest
    @MethodSource("getNegativePageNumber")
    void whenPageNumberIsNegative_thenReturnError(CategoryFilter filter) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/category")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.
                readStringFromResources("/response/category/web/v1/error_pageNumber_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("getNegativePageSize")
    void whenPageSizeIsNegative_orIsZero_thenReturnError(CategoryFilter filter) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/category")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.
                readStringFromResources("/response/category/web/v1/error_pageSize_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("payloadData")
    void whenCategoryPayloadValidError_thenReturnError(PayloadData payload) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        post("/api/v1/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload.payload)))
                .andExpect(status().isBadRequest()).andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");
        assertNotNull(response.getContentAsString());
        assertFalse(response.getContentAsString().trim().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("getCategoryId")
    void whenCategoryIdValidError_thenReturnError(CategoryId id) throws Exception {
        String actual = mvc.perform(delete("/api/v1/category/{id}", id.getId()))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        assertNotNull(actual);
        assertFalse(actual.trim().isEmpty());
    }

    private record PayloadData (CategoryPayload payload, int number){}
}