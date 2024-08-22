package com.example.newsservice2.config;

import com.example.newsservice2.testUtils.TestDbFacade;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureTestEntityManager
@ContextConfiguration(classes = TestDataConfig.class)
public class AbstractIntegrationTest {
    @Autowired
    private TestDbFacade facade;

    public TestDbFacade getFacade() {
        return facade;
    }

    protected static PostgreSQLContainer<?> postgreSQLContainer;

    static {
        DockerImageName postgres = DockerImageName.parse("postgres:14.5");
        postgreSQLContainer = new PostgreSQLContainer<>(postgres);

        postgreSQLContainer.withReuse(true);
        postgreSQLContainer.withDatabaseName("news_service");
        postgreSQLContainer.withInitScript("static/schema.sql");
    }

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.jpa.generate-ddl", () -> true);
        registry.add("spring.datasource.driver-class-name",() -> postgreSQLContainer.getDriverClassName());
    }

    @AfterEach
    void tearDown() {
        facade.cleanDatabase();
    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }
}
