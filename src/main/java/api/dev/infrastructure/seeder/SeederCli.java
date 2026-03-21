package api.dev.infrastructure.seeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(
    scanBasePackages = "api.dev",
    exclude = {
        SecurityAutoConfiguration.class,            // ← skip Spring Security entirely
        SecurityFilterAutoConfiguration.class       // ← skip security filter chain
    }
)
public class SeederCli {

    public static void main(String[] args) {
        var app = new SpringApplication(SeederCli.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        System.exit(SpringApplication.exit(app.run(args)));
    }
}
