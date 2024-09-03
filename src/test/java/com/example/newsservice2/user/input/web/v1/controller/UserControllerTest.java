package com.example.newsservice2.user.input.web.v1.controller;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.testUtils.StringTestUtils;
import com.example.newsservice2.user.input.web.v1.model.UserFilter;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.model.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
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

import static com.example.newsservice2.testUtils.testBuilder.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TypeExcludeFilters(DataJpaTypeExcludeFilter.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@Testcontainers
class UserControllerTest extends AbstractIntegrationTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mvc;

    public static Stream<Arguments> getPagination() {
        UserFilter f1 = new UserFilter();
        f1.setPageNumber(0);
        f1.setPageSize(2);
        UserFilter f2 = new UserFilter();
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

    @ParameterizedTest
    @MethodSource("getPagination")
    void whenGetAll_ThenReturnPageSizeAndPageNumber(UserFilter filter) throws Exception {
        getFacade().save(aUser().withEmail("test1@email.test"));
        getFacade().save(aUser().withEmail("test2@email.test"));
        getFacade().save(aUser().withEmail("test3@email.test"));
        getFacade().save(aUser().withEmail("test4@email.test"));
        getFacade().save(aUser().withEmail("test5@email.test"));

        String actual = mvc.perform(
                        get("/api/v1/user")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/response/user/web/v1/find_by_filter_" + filter.getPageNumber() + "_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("users[*].id"));
    }

    @Test
    void whenCreateNewUser_thenReturnUser() throws Exception {
        UserPayload userPayload = new UserPayload();
        userPayload.setEmail("email@email.test");
        userPayload.setNickName("Vasya");

        String actual = mvc.perform(
                        post("/api/v1/user")
                                .content(objectMapper.writeValueAsString(userPayload))
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        String expected = StringTestUtils
                .readStringFromResources("/response/user/web/v1/create_new_category_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("id"));
    }

    @Test
    void whenUpdateUser_thenReturnUpdatedUser() throws Exception {
        UserEntity aldUser = getFacade().save(aUser());

        UserPayload payload = new UserPayload();
        payload.setNickName("Ne Vasya");
        payload.setEmail("test2@email.test");

        String actual = mvc.perform(
                        put("/api/v1/user/{id}", aldUser.getId())
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        String expected = StringTestUtils
                .readStringFromResources("/response/user/web/v1/update_user_response.json");

        JsonAssert.assertJsonEquals(expected, actual, JsonAssert.whenIgnoringPaths("id"));
    }

    @Test
    void whenDelete_thanReturnNoContent() throws Exception {
        mvc.perform(delete("/api/v1/user/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @ParameterizedTest
    @MethodSource("getNegativePageNumber")
    void whenPageNumberIsNegative_thenReturnError(CategoryFilter filter) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/user")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.
                readStringFromResources("/response/user/web/v1/error_pageNumber_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("getNegativePageSize")
    void whenPageSizeIsNegative_orIsZero_thenReturnError(CategoryFilter filter) throws Exception {
        MockHttpServletResponse response = mvc.perform(
                        get("/api/v1/user")
                                .param("pageNumber", String.valueOf(filter.getPageNumber()))
                                .param("pageSize", String.valueOf(filter.getPageSize())))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.
                readStringFromResources("/response/user/web/v1/error_pageSize_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }

    @Test
    void whenUserNotFound_thenReturnError() throws Exception {
        UserPayload payload = new UserPayload();
        payload.setEmail("test@email.test");
        payload.setNickName("Vasya");

        long id = Long.MAX_VALUE;
        MockHttpServletResponse response = mvc.perform(
                        put("/api/v1/user/{id}", id)
                                .content(objectMapper.writeValueAsString(payload))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");
        assertNotNull(response.getContentAsString());
        assertFalse(response.getContentAsString().trim().isEmpty());
    }

    @Test
    void whenEmailIsNotValid_thenReturnError() throws Exception {
        UserPayload payload = new UserPayload();
        payload.setNickName("Vasya");
        payload.setEmail("asd");

        MockHttpServletResponse response = mvc.perform(
                        post("/api/v1/user")
                                .content(objectMapper.writeValueAsString(payload))
                                .contentType(APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        response.setCharacterEncoding("UTF-8");

        String actual = response.getContentAsString();

        String expected = StringTestUtils.readStringFromResources("/response/user/web/v1/email_error_response.json");

        JsonAssert.assertJsonEquals(expected, actual);
    }
}