package com.example.testcontainer;

import com.example.testcontainer.repository.User;
import com.example.testcontainer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TestContainerTest {

    Logger logger = LoggerFactory.getLogger(TestContainerTest.class);
    @Autowired
    private UserRepository userRepository;
    @Container
    public PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:13.8");

    @Test
    void testContainerRunningStatus() {
        assertThat(postgres.isRunning()).isTrue();
        logger.info("Container ID: " + postgres.getContainerId());
        logger.info("JDBC URL: " + postgres.getJdbcUrl());
        logger.info("Exposed ports: " + postgres.getExposedPorts());
        logger.info("Mapped port to 5432: " + postgres.getMappedPort(5432));
    }

    @Test
    void addNewUserAndGetCount() {
        User user = new User(1,"Sahana");
        userRepository.save(user);
        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
    }
}
