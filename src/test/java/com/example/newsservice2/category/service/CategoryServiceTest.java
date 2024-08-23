package com.example.newsservice2.category.service;

import com.example.newsservice2.category.input.web.v1.model.CategoryFilter;
import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;
import com.example.newsservice2.category.model.CategoryEntity;
import com.example.newsservice2.config.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static com.example.newsservice2.category.input.web.v1.controller.testBuilder.CategoryTestDataBuilder.aCategory;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@ContextConfiguration(classes = CategoryServiceConf.class)
class CategoryServiceTest extends AbstractIntegrationTest {
    @Autowired
    private CategoryService service;

    @Test
    void whenGetByFilter_thenReturnAllCategories() throws Exception {
        getFacade().save(aCategory().withName("test1"));
        getFacade().save(aCategory().withName("test2"));
        getFacade().save(aCategory().withName("test3"));
        getFacade().save(aCategory().withName("test4"));
        getFacade().save(aCategory().withName("test5"));
        getFacade().save(aCategory().withName("test6"));
        getFacade().save(aCategory().withName("test7"));

        CategoryFilter filter = new CategoryFilter();
        filter.setPageSize(3);
        filter.setPageNumber(0);
        List<CategoryEntity> actual = service.getByFilter(filter);

        assertAll(() -> {
            assertNotNull(actual);
            assertFalse(actual.isEmpty());
            assertEquals(3, actual.size());
        });
    }

    @Test
    void whenCreateNewCategory_ThenSaveCategoryIntoDatabase_andReturnCategory() throws Exception {
        CategoryPayload payload = new CategoryPayload();
        payload.setName("new_category_name");

        CategoryEntity actual = service.createNewCategory(payload);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(payload.getName(), actual.getName());
            CategoryEntity entity = getFacade().find(actual.getId(), CategoryEntity.class);

            assertEquals(payload.getName(), entity.getName());
        });
    }

    @Test
    void whenUpdateCategory_thenSaveChange_andReturnUpdatedCategory() throws Exception {
        CategoryEntity oldCategory = getFacade().save(aCategory());
        CategoryPayload payload = new CategoryPayload();

        payload.setName("new_category_name");
        CategoryEntity actual = service.updateCategory(oldCategory.getId(), payload);

        assertAll(() -> {
            assertNotNull(actual);
            assertEquals(payload.getName(), actual.getName());
            assertEquals(oldCategory.getId(), actual.getId());
            assertEquals(getFacade().find(oldCategory.getId(), CategoryEntity.class).getName(), payload.getName());
        });
    }
}