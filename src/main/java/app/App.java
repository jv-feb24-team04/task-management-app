package app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@OpenAPIDefinition(
        info = @Info(
                title = "Task management application",
                version = "0.1",
                description = "APIs for managing task manager"
        )
)
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

