package task.ing.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "ING Hubs TR Banking Project",
                version = "1.0.0,",
                description = "This API can be using for basic banking services"

        )
)
public class OpenApiConfig {
}
