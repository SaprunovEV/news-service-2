package com.example.newsservice2.testUtils.testBuilder;

import com.example.newsservice2.testUtils.TestDataBuilder;
import com.example.newsservice2.user.model.UserEntity;

public class UserTestDataBuilder implements TestDataBuilder<UserEntity> {
    private String nickName = "Vasya";
    private String email = "vasya@emai.test";

    private UserTestDataBuilder() {}

    private UserTestDataBuilder(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;
    }

    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }

    public UserTestDataBuilder withNickName(String nickName) {
        return this.nickName == nickName ? this : new UserTestDataBuilder(nickName, email);
    }

    public UserTestDataBuilder withEmail(String email) {
        return this.email == email ? this : new UserTestDataBuilder(this.nickName, email);
    }

    @Override
    public UserEntity build() {
        UserEntity result = new UserEntity();
        result.setEmail(this.email);
        result.setNickName(this.nickName);
        return result;
    }
}
