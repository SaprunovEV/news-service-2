package com.example.newsservice2.user.service;

import com.example.newsservice2.config.AbstractIntegrationTest;
import com.example.newsservice2.user.input.web.v1.model.UserFilter;
import com.example.newsservice2.user.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.example.newsservice2.testUtils.testBuilder.UserTestDataBuilder.aUser;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@ContextConfiguration(classes = UserServiceConfig.class)
class UserServiceTest extends AbstractIntegrationTest {
    @Autowired
    private UserService service;

    @Test
    void whenGetAll_thenReturnPageOfUser() throws Exception {
        getFacade().save(aUser());
        getFacade().save(aUser());
        getFacade().save(aUser());
        getFacade().save(aUser());
        getFacade().save(aUser());


        UserFilter filter = new UserFilter();
        filter.setPageNumber(0);
        filter.setPageSize(3);

        List<UserEntity> actual = service.findAll(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertFalse(actual.isEmpty());
            assertEquals(3, actual.size());
        });
    }

    @Test
    void whenCreateNewUser_thenSaveNewUserToDatabase() throws Exception {
        UserEntity payload = aUser().build();

        UserEntity result = service.createUser(payload);

        assertAll(() -> {
            assertNotNull(result);
            UserEntity actual = getFacade().find(result.getId(), UserEntity.class);
            assertNotNull(actual);
            assertUserEquals(payload, actual);
        });
    }

    @Test
    void whenUpdateUser_thenSaveNewDataToDatabase() throws Exception {
        UserEntity oldUser = getFacade().save(aUser());

        UserEntity newUser = aUser()
                .withEmail("test2@email.test")
                .withNickName("Ne Vasya")
                .build();

        UserEntity result = service.updateUser(oldUser.getId(), newUser);

        assertAll(() -> {
            assertNotNull(result);
            UserEntity actual = getFacade().find(oldUser.getId(), UserEntity.class);
            assertUserEquals(newUser, actual);
        });
    }

    @Test
    void whenDelete_thenDeleteUserFromDatabase() throws Exception {
        UserEntity saved = getFacade().save(aUser());

        service.deleteUser(saved.getId());

        assertNull(getFacade().find(saved.getId(), UserEntity.class));
    }

    private void assertUserEquals(UserEntity payload, UserEntity actual) {
        assertAll(() -> {
            assertEquals(payload.getNickName(), actual.getNickName());
            assertEquals(payload.getEmail(), actual.getEmail());
        });
    }
}