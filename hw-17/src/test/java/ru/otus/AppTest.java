package ru.otus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.ClientDetails;
import ru.otus.crm.model.Manager;
import ru.otus.crm.repository.ManagerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AppTest extends AbstractIntegrationTest {

    @Autowired
    private ManagerRepository managerRepository;

    @BeforeEach
    void setUp() {
        managerRepository.deleteAll();
    }

    @Test
    @Order(1)
    void should_be_zero_after_delete() throws Exception {
        assertEquals(0,managerRepository.count());
    }

    @Test
    @Order(2)
    void should_be_return_one_saved_manager() throws Exception {

        var managerName = "m:" + System.currentTimeMillis();
        managerRepository.saveAll(List.of(new Manager(managerName, "ManagerSecond",
                Set.of(new Client("managClient1", managerName, 1, new ClientDetails("inf01")),
                        new Client("managClient2", managerName, 2, new ClientDetails("info2"))),
                new ArrayList<>(), true)));

        assertThat(managerRepository.findAll()).hasSize(1);
    }

}