package com.example.newsservice2.testUtils.testBuilder;

import com.example.newsservice2.category.model.CategoryEntity;
import com.example.newsservice2.testUtils.TestDataBuilder;

public class CategoryTestDataBuilder implements TestDataBuilder<CategoryEntity> {
    private String name = "test_name";

    private CategoryTestDataBuilder() {}

    private CategoryTestDataBuilder(String name) {
        this.name = name;
    }

    public static CategoryTestDataBuilder aCategory() {
        return new CategoryTestDataBuilder();
    }



    @Override
    public CategoryEntity build() {
        CategoryEntity result = new CategoryEntity();
        result.setName(this.name);
        return result;
    }

    public CategoryTestDataBuilder withName(String name) {
        return this.name == name ? this : new CategoryTestDataBuilder(name);
    }
}
