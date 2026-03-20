package api.dev;

import api.dev.infrastructure.seeder.UserMasterSeeder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseIntegrationTest {

    @Autowired protected MockMvc mockMvc;
    @Autowired protected JdbcTemplate jdbcTemplate;
    @Autowired protected UserMasterSeeder masterSeeder;

    /**
     * Step 1 — verify DB connection
     * Step 2 — migrations run automatically via Liquibase on context startup
     * Step 3 — seed via the real application seeders
     */
    @BeforeAll
    void setupDatabase() {
        // Step 1 — verify connection (throws immediately if DB is unreachable)
        jdbcTemplate.queryForObject("SELECT 1", Integer.class);

        // Step 3 — reuse real seeder (same logic, same data as local dev)
        masterSeeder.seed();
        // adminSeeder.seed();
        // employeeSeeder.seed();
    }

    protected void cleanDatabase() {
        jdbcTemplate.execute(
            "TRUNCATE TABLE master_access_logs, master_profile, masters, users " +
            "RESTART IDENTITY CASCADE"
        );
    }
}
