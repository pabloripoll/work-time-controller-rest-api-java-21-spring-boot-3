package api.dev.infrastructure.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile({"dev", "local"})
public class DatabaseSeeder implements CommandLineRunner {

    private final UserMasterSeeder masterSeeder;
    private final ApplicationContext context;

    // Inject all seeders here
    public DatabaseSeeder(
            UserMasterSeeder masterSeeder,
            ApplicationContext context) {
        this.masterSeeder = masterSeeder;
        this.context = context;
    }

    @Override
    public void run(String... args) {

        if (!Arrays.asList(args).contains("--seed")) {
            return;
        }

        System.out.println("Starting Database Seeding...");

        // 2. RUN IN THE EXACT RIGHT ORDER TO AVOID FOREIGN KEY ERRORS
        masterSeeder.seed();
        // geoLocationSeeder.seed();
        // officeSeeder.seed();
        // employeeSeeder.seed();
        // adminSeeder.seed();

        System.out.println("Database Seeding Complete!");

        // 3. Shut down the script so it acts like a CLI tool
        System.exit(SpringApplication.exit(context, () -> 0));
    }
}
