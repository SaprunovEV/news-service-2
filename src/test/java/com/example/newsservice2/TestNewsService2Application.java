package com.example.newsservice2;

import org.springframework.boot.SpringApplication;

public class TestNewsService2Application {

    public static void main(String[] args) {
        SpringApplication.from(NewsService2Application::main).with(TestcontainersConfiguration.class).run(args);
    }

}
