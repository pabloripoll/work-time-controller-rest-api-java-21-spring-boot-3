package api.dev.infrastructure.seeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;

@SpringBootApplication(exclude = {
    WebMvcAutoConfiguration.class,
    EmbeddedWebServerFactoryCustomizerAutoConfiguration.class
})
public class SeederMain {
    public static void main(String[] args) {
        var app = new SpringApplication(SeederMain.class);
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE); // ← no Tomcat
        System.exit(SpringApplication.exit(app.run(args)));
    }
}
