package com.example.newsservice2.testUtils;

import com.example.newsservice2.category.input.web.v1.model.CategoryPayload;

public class CategoryPayloadTestDataBuilder implements TestDataBuilder<CategoryPayload> {
    private String name = "category_test";

    private CategoryPayloadTestDataBuilder() {}

    public CategoryPayloadTestDataBuilder(String name) {
        this.name = name;
    }

    public static CategoryPayloadTestDataBuilder aCategoryPayload() {
        return new CategoryPayloadTestDataBuilder();
    }

    public CategoryPayloadTestDataBuilder withName(String name) {
        return name == this.name ? this : new CategoryPayloadTestDataBuilder(name);
    }

    @Override
    public CategoryPayload build() {
        CategoryPayload result = new CategoryPayload();
        result.setName(this.name);
        return result;
    }
}
