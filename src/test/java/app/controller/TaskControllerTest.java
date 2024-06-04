package app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.dto.task.TaskResponseDto;
import app.model.Priority;
import app.model.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {
    private static final String PROJECT_ID_1 = "?projectId=1";
    protected static MockMvc mockMvc;
    private static final String TASK_URL = "/api/tasks";

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext context,
            @Autowired DataSource dataSource) {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
    }

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) {
        addThreeTasks(dataSource);
    }

    @AfterEach
    void tearDown(@Autowired DataSource dataSource) {
        teardown(dataSource);
    }

    @Test
    @WithMockUser
    public void getAllByProjectId_GivenTasks_ReturnsAllTasks() throws Exception {
        TaskResponseDto configureRepo = new TaskResponseDto();
        configureRepo.setProjectId(1L);
        configureRepo.setName("Configure repo");
        configureRepo.setPriority(Priority.MEDIUM);
        configureRepo.setStatus(TaskStatus.IN_PROGRESS);
        configureRepo.setDueDate(LocalDate.of(2024, 8, 1));
        configureRepo.setProjectId(1L);
        configureRepo.setAssigneeId(1L);
        configureRepo.setComments(Set.of());
        configureRepo.setLabels(List.of());

        TaskResponseDto addUser = new TaskResponseDto();
        addUser.setProjectId(2L);
        addUser.setName("Add User");
        addUser.setPriority(Priority.HIGH);
        addUser.setStatus(TaskStatus.NOT_STARTED);
        addUser.setDueDate(LocalDate.of(2024, 8, 2));
        addUser.setProjectId(1L);
        addUser.setAssigneeId(2L);
        addUser.setComments(Set.of());
        addUser.setLabels(List.of());

        List<TaskResponseDto> expected = new ArrayList<>();
        expected.add(configureRepo);
        expected.add(addUser);

        MvcResult result = mockMvc.perform(get(TASK_URL + PROJECT_ID_1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TaskResponseDto[] actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TaskResponseDto[].class);

        assertNotNull(actual);
        assertEquals(expected, Arrays.stream(actual).toList());
    }

    @SneakyThrows
    private static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/teardown.sql"));
        }
    }

    @SneakyThrows
    private void addThreeTasks(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/users/add-two-users.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/projects/add-two-projects.sql"));
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/tasks/add-three-tasks.sql"));
        }
    }
}
