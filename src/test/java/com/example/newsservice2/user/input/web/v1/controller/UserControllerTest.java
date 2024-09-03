package com.example.newsservice2.user.input.web.v1.controller;

import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.testUtils.StringTestUtils;
import com.example.newsservice2.user.input.web.v1.model.UserFilter;
import com.example.newsservice2.user.input.web.v1.model.UserPayload;
import com.example.newsservice2.user.model.UserEntity;
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
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.stream.Stream;

import static com.example.newsservice2.testUtils.testBuilder.UserTestDataBuilder.aUser;
import static org.springframework.http.MediaType.APPLICATION_JSON;
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
}