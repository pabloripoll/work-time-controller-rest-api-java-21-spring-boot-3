<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER

- [/README.md](../README.md)
<br><br>

This documentation describes the usage of this project repository within the Docker platforms repository

- https://github.com/pabloripoll/docker-platforms-nginx-java-21-maven-pgsql-18.2

## Database Service

Before starting up with the REST API container, the Platforms respository comes with a Postgres platform to be run in a separated container.

As if the API was using a remote database instance, for e.g. an already existing AWS RDS instance.

If you are in local environment and needing a database service for development, you can build up and run the Postgres container service.

Set its parameters in the upper layer `.env` file, for e.g.:
```sh
DATABASE_PLTF=pgsql-18.2
DATABASE_IMGK=postgres:18.2-alpine3.23
DATABASE_PORT=7750
DATABASE_CAAS=jsb-pgsql-dev
DATABASE_CAAS_CPUS=2.00
DATABASE_CAAS_MEM=128M
DATABASE_CAAS_SWAP=256M
DATABASE_ROOT="worktic_root"
DATABASE_NAME=worktic_local
DATABASE_USER=worktic_user
DATABASE_PASS="worktic_pass"
DATABASE_PATH="../../resources/database/" # here you can save snapshots
DATABASE_INIT=pgsql-init.sql
DATABASE_BACK=pgsql-backup.sql
```

Once the variables required for the service `docker-compose.yml` file were manually set, you can build the container
```sh
$ make db-set # sets the API container required params
$ make db-create # builds up the API container
```

After building the container, you can checkout its main information
```sh
$ make db-info

WORKTIC JAVA SPRINGBOOT: POSTGRES 18.2
Container ID.: e92d5baa33f7
Name.........: worktic-java-sb-pgsql-dev
Image........: worktic-java-sb-pgsql-dev:postgres:18.2-alpine3.23
CPUs.........: 2.00
RAM..........: 128M
SWAP.........: 256M
Host.........: 127.0.0.1:7750
Hostname.....: 192.168.1.41:7750 # IP:PORT to connect with DB
Docker.Host..: 172.19.0.2
NetworkID....: 9a1004c8db7e4181c71bcad8719f13959e6b0f594f4b88bcc8f387677f6c2f0b
```
<br><br>


## REST API Service

### 1. Java

This repository comes with two Dockerfiles, one is for continuing the REST API developement and thus use the Dockerfile.JDK. Otherwise, to just run the end application, use the Dockerfile.JRE instead.

So to set the correct Dockerfile to build the container, choose one of them and copy it as e.g.
```bash
$ cp ./docker/Dockerfile.JDK ./docker/Dockerfile
```

### 2. NGINX

Copy `./platforms/nginx-java-21/docker/config/nginx/conf.d-sample/default.conf` into `./platforms/nginx-java-21/docker/config/nginx/conf.d/default.conf`

### 3. Supervisor

Before building up and running the container, copy from `./platforms/nginx-java-21/docker/config/supervisor/conf.d-sample` the Supervisor services to run the main API version and/or the local development version:

- `./platforms/nginx-java-21/docker/config/supervisor/conf.d/nginx.conf` *(mandatory)*

Remember that the following Supervisor services needs database schema migrated and/or seeding
- `./platforms/nginx-java-21/docker/config/supervisor/conf.d/java-jar.conf` *(Only when running the .jar file)*
- `./platforms/nginx-java-21/docker/config/supervisor/conf.d/java-dev.conf` *(Development mode has hot reloading)*

### 4. REST API container set up

**REMEMBER:** Set the container NGINX default server block configuration file at `./docker/config/nginx/conf.d/default.conf`

Once the variables required for the service `docker-compose.yml` file were manually set, you can build the container
```sh
$ make apirest-set # sets the API container required params
$ make apirest-create # builds up the API container
```

After building the container, you can checkout its main information
```sh
$ make apirest-info

WORKTIC JAVA SPRINGBOOT: NGINX - JAVA 21
Container ID.: de62596bfeb8
Name.........: worktic-java-sb-apirest-dev
Image........: worktic-java-sb-apirest-dev:alpine3.22-nginx1.28-java21
Memory.......: 512M
Host.........: 127.0.0.1:7501       # IP:PORT for the compiled API
Hostname.....: 192.168.1.41:7501
Docker.Host..: 172.20.0.2           # Docker-IP:8081 for the API in development
NetworkID....: 142897d77adf48319c5cb5113b59023ede7a6a2fe58d0d2e0193aeac08f9df1f
```

The Platforms repository doesn't come with SSL provider as that depends on an upper infrastructure layer.

### 5. Platform Content

Everytime you need to access into container, use the recipe
```bash
$ make apirest-ssh
```

Checkout O.S. Linux
```bash
/var/www $ cat /etc/os-release
NAME="Alpine Linux"
ID=alpine
VERSION_ID=3.23.3
PRETTY_NAME="Alpine Linux v3.23"
HOME_URL="https://alpinelinux.org/"
BUG_REPORT_URL="https://gitlab.alpinelinux.org/alpine/aports/-/issues"
```

Checkout NGINX version
```bash
/var/www $ nginx -v
nginx version: nginx/1.28.2
```

Checkout Java version
```bash
/var/www $ java -version
openjdk version "21.0.10" 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-21.0.10+7 (build 21.0.10+7-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.10+7 (build 21.0.10+7-LTS, mixed mode, sharing)
```

Checkout Maven version
```bash
$ mvn --version
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: /usr/share/java/maven-3
Java version: 21.0.10, vendor: Eclipse Adoptium, runtime: /opt/java/openjdk
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.12.38+kali-amd64", arch: "amd64", family: "unix"
```
<br>

### 6. Run Migrations

It is important to run the migration before the API is up and running
```bash
/var/www $ mvn liquibase:update
```

## Seeders

### Option 1: Seed by command

The command to run the seeder *(only if supervisord is not running the app)* is:
```bash
$ mvn spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.arguments=--seed
```

The `-Dspring-boot.run.profiles=dev` activates the @Profile({"dev", "local"}) on DatabaseSeeder and UserMasterSeeder. Without it, Spring won't load those beans and nothing runs.

#### Summary — three ways to seed

- Command: `$ mvn spring-boot:run -Dspring-boot.run.arguments=--seed -Dspring-boot.run.profiles=dev`
    - Profile Env.: development
    - What runs: DatabaseSeeder → masterSeeder.seed() → exits

- Command: `$ mvn spring-boot:run -Dspring-boot.run.profiles=dev`
    - Profile Env.: development
    - What runs: App starts normally, no seeding

- Command: `$ mvn test`
    - Profile Env.: test
    - What runs: BaseIntegrationTest.setupDatabase() → masterSeeder.seed() directly

### Option 2 — Seeder Cli

A dedicated exec profile that runs a standalone main() in a separate process — no port conflict with the running app it does not boot a full Spring context, so JPA repositories are never registered — to run a main seeders instead of option 1 commands.
```bash
/var/www $ mvn exec:java -Pseed
```
<br><br>


## Development

Since the container is using Supervisor, it is the "boss" of the API process. You should never try to kill a Supervisor-managed process manually or with Maven command because Supervisor will think that the API service has crashed! Avoid the "Supervisor Trap" (Foreground vs. Background) as it has one very strict rule: It only manages programs that run in the foreground. It will mark the process as `FATAL` or `EXITED` and the Java app is still secretly running in the background as a "zombie" process.

- mvn spring-boot:run runs in the foreground. It blocks the terminal and streams the logs continuously. Supervisor loves this because it can hold onto the process, read the logs, and know exactly when it stops.

- mvn spring-boot:start is designed to start the app in the background (daemon mode) and immediately return control of the terminal to you.

It is not a big problem, but it is important to avoid destroying and building the container continuosly.

As supervisor manage `$ mvn spring-boot:run` you can stop that service from within the container
```bash
/var/www $ sudo supervisorctl stop java-dev
```

Local development version on port 8081 - `$ mvn spring-boot:run` - use `$ make apirest-info` to discover docker assigned port
```bash
/var/www $ sudo supervisorctl start java-dev
```

Then, you can use if needed
```bash
/var/www $ mvn spring-boot:start
/var/www $ mvn spring-boot:stop

# Or just
/var/www $ mvn spring-boot:run
```

For compiling lastest API version, NGINX proxy to app.jar file running on port 8080
```bash
/var/www $ sudo supervisorctl start java-jar
```

### When start and stop commands are actually use for?

In the professional Java world, spring-boot:start and spring-boot:stop are almost exclusively used for Automated Integration Testing inside the pom.xml, not for running the actual development server.

#### A CI/CD pipeline (like GitHub Actions) will use it like this:

- `mvn spring-boot:start` (Starts the app in the background)
- `mvn failsafe:integration-test` (Fires HTTP requests at the background app to test it)
- `mvn spring-boot:stop` (Kills the background app so the pipeline can finish)
<br>

## Developing features

Since there is spring-boot-devtools in your pom.xml:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

You should not need to restart manually. DevTools watches for file changes and triggers an automatic restart when it detects them.

However there are cases where a manual restart is still needed:

| Change type	                    |    Auto-restart	            | Manual restart needed          |
| --------------------------------- | ----------------------------- | ------------------------------ |
| Controller / service logic	    | ✅	                           | ❌                             |
| application.properties values	    | ✅	                           | ❌                             |
| New @Bean definition	            | ✅	                           | ❌                             |
| pom.xml dependency added	        | ❌	                           | ✅                             |
| .env file changes	                | ❌	                           | ✅                             |
| Static resources (HTML/CSS/JS)	| ✅ live reload	               | ❌                             |
| Database schema / Liquibase	    | ❌	                           | ✅                             |

So for your case — if you only changed Java files — DevTools should have picked it up automatically. If changes are not reflecting, the quickest check is to stop Supervisor:

```bash
# stop supervisor service
$ sudo supervisorctl stop java-dev
# run manually
$ mvn spring-boot:run
# after above command, if neccessary
$ mvn compile
```

Quickest check — what command starts your app:
```bash
# see what is running inside container
$ ps aux | grep java
```

| Startup command	                | How to apply changes                              |
| --------------------------------- | ------------------------------------------------- |
| mvn spring-boot:run	            | Ctrl+C → mvn spring-boot:run                      |
| java -jar target/*.jar	        | mvn package -DskipTests → restart                 |
| mvn compile + DevTools	        | mvn compile → DevTools restarts automatically     |

Stop and re-run it — it also picks up `.env` and `application.properties` changes at the same time, which DevTools would miss anyway.
<br><br>

## Create the REST API compiled file

Access into container to create the Application JAR file
```sh
$ make apirest-ssh

/var/www $ mvn -U clean package

[INFO] Scanning for projects...
Downloading ...
[INFO] Replacing main artifact /var/www/target/api-springboot-0.0.1-SNAPSHOT.jar with repackaged archive, adding nested dependencies in BOOT-INF/.
[INFO] The original artifact has been renamed to /var/www/target/api-springboot-0.0.1-SNAPSHOT.jar.original
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  24.314 s
[INFO] Finished at: 2025-10-25T13:41:49Z
[INFO] ------------------------------------------------------------------------
```

- Create the JAR file
```sh
/var/www $ java -jar target/api-springboot-0.0.1-SNAPSHOT.jar
/var/www $ cp target/api-springboot-0.0.1-SNAPSHOT.jar target/app.jar
```

- Manual Testing:
    Visit http://127.0.0.1:[main-platform-port-set]/

All toghether:
```bash
/var/www $ mvn clean package && cp target/api-springboot-0.0.1-SNAPSHOT.jar target/app.jar && supervisorctl -c /etc/supervisor/supervisord.conf reload
```

### Options

#### mvn -U clean package

- Forces Maven to check for updated SNAPSHOT dependencies from remote repositories
- Runs the full lifecycle: compile → test → package
- Produces the JAR in target/
- Use when: you want a fresh build with latest snapshots and need the artifact

#### mvn -U clean test

- Forces snapshot dependency updates same as above
- Runs compile → test but stops before packaging
- No JAR is produced
- Use when: you only want to verify tests pass with latest dependencies

#### mvn clean package -DskipTests

- Does not force snapshot updates
- Compiles and packages into a JAR but skips all tests entirely (not even compiling test classes)
- Fastest way to get a JAR
- Use when: you need a quick build for deployment and tests were already verified separately

### Quick rule of thumb

```sh
-U              → refresh dependencies from remote repo
clean package   → full build with tests → produces JAR
clean test      → verify tests only     → no JAR
-DskipTests     → skip tests entirely   → fastest JAR
```

## Testing

I always try to set as many end-to-end tests against API contracts as possible because it gives me a solid confidence on the API reliability.

The strategy is to run level 3 End-to-end / Persistence tests:
```sh
┌─────────────────────────────────────────────────────────┐
│  Level 1 — Unit tests                                   │
│  No Spring, no DB, no network                           │
│  MasterTest, JwtServiceTest, CreateMasterUseCaseTest    │
│  Speed: ~milliseconds                                   │
├─────────────────────────────────────────────────────────┤
│  Level 2 — Integration tests (what you have now)        │
│  Spring context + MockMvc + @MockBean (no real DB)      │
│  MasterAuthControllerTest, MasterAccountControllerTest  │
│  Speed: ~seconds (context startup cost)                 │
├─────────────────────────────────────────────────────────┤
│  Level 3 — End-to-end / persistence tests               │
│  Real DB required — NOT implemented yet                 │
│  Would use @DataJpaTest or Testcontainers               │
│  Speed: ~tens of seconds                                │
└─────────────────────────────────────────────────────────┘
```

Testing flow
```bash
mvn test
   │
   ├── Spring context starts
   │     └── Liquibase runs migrations on worktic_local_test   ← Step 2
   │
   ├── BaseIntegrationTest.setupDatabase()
   │     ├── SELECT 1  ← Step 1: verify connection
   │     └── INSERT master@webmaster.com etc.  ← Step 3: seed
   │
   ├── @Order(1..4) tests run  ← Step 4: test endpoints, cache JWT
   │
   └── @AfterAll cleanDatabase()
         └── TRUNCATE all tables  ← clean slate for next run
```

To run test is neccessary to access into API container
```bash
# Run all tests
/var/www/ $ mvn test

# Run only a specific class
/var/www/ $ mvn test -Dtest=JwtServiceTest

# Run with verbose output
/var/www/ $ mvn test -Dsurefire.reportFormat=plain

# Run test script
/var/www $ mvn test -Dspring.profiles.active=test -Dtest=MasterAccountControllerTest

# Run test script method
/var/www $ mvn test -Dspring.profiles.active=test -Dtest=MasterAccountControllerTest#getProfile_authenticated_returns200
```

### What each test layer validates

| Test class	                   | Validates                                                          |
| -------------------------------- | ------------------------------------------------------------------ |
| MasterTest	                   | Domain business rules — activate, ban, unban                       |
| MasterProfileTest                | Validation on nickname, avatar management                          |
| CreateMasterUseCaseTest	       | Orchestration, duplicate email guard                               |
| JwtServiceTest	               | Token generation, parsing, expiry, tamper detection                |
| JwtAuthenticationFilterTest	   | Filter passes/blocks correctly, sets SecurityContext               |
| MasterAuthControllerTest         | Full HTTP login flow — success, wrong password, unknown email      |
| MasterAccountControllerTest	   | Role guard (401/403), authenticated profile read/update            |
<br>

## Storage

Functional/MockMvc and not E2E for files storage:

| Concern                           | MockMvc (functional)	        | E2E                           |
| --------------------------------- | ----------------------------- | ----------------------------- |
| ImageUploadValidator logic	    | tested via 422 cases          | But slow                      |
| File actually written to disk	    | mocked — intentional	        | But needs cleanup             |
| Auth / role enforcement	        | 401 + 403 cases	            | But needs real DB             |
| Speed	                            | Fast	                        | Slow                          |

The actual file writing (AvatarStorageLocalService) is a candidate for a separate focused unit test that just calls store() directly with a temp directory — no Spring context needed.
<br><br>

## Development commands overview

### Tests

```bash
# Run all tests
/var/www $ mvn test

# Run a specific test class
/var/www $ mvn test -Dtest=MasterAuthControllerTest

# Run a specific test method
/var/www $ mvn test -Dtest=MasterAuthControllerTest#methodName

# Run tests matching a pattern
/var/www $ mvn test -Dtest="Master*"

# Skip tests during build
/var/www $ mvn clean package -DskipTests
```

### Liquibase Migrations

```bash
# Apply pending migrations (uses src/main/resources/liquibase.properties)
/var/www $ mvn liquibase:update

# Check pending changesets (dry run / status)
/var/www $ mvn liquibase:status

# Rollback last N changesets
/var/www $ mvn liquibase:rollback -Dliquibase.rollbackCount=1

# Generate a diff between DB and changelog
/var/www $ mvn liquibase:diff

# Validate the changelog
/var/www $ mvn liquibase:validate
```

### Database Seeder

```bash
# Run the seeder (uses the "seed" Maven profile → SeederCli main class)
/var/www $ mvn exec:java -Pseed
```

### Useful Combos

```bash
# Clean build + migrate + run
/var/www $ mvn clean package -DskipTests && mvn liquibase:update && mvn spring-boot:run

# Full cycle: clean, test, package
/var/www $ mvn clean test package

# Run with a specific Spring profile
/var/www $ mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>