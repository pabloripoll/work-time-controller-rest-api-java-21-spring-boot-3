<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# API Development

This documentation describe the usage of the Platform Repository

### 1. Java

This repository comes with two Dockerfiles. If you want to be able to compile Java code (not just run it), you need the Dockerfile.JDK. Otherwise, to just run the end application, the Dockerfile.JRE.

So to set the correct Dockerfile to build the container, choose one of them and copy
```bash
$ cp ./docker/Dockerfile.JDK ./docker/Dockerfile
```

### 2. NGINX

Copy `./platform/nginx-java-21/docker/config/nginx/conf.d-sample/default.conf` into `./platform/nginx-java-21/docker/config/nginx/conf.d/default.conf`

### 3. Supervisor

Before build up and run the container, copy from `./platform/nginx-java-21/docker/config/supervisor/conf.d-sample` the Supervisor services to run the main API version and/or the local development version:

- ./platform/nginx-java-21/docker/config/supervisor/conf.d/nginx.conf
- ./platform/nginx-java-21/docker/config/supervisor/conf.d/java-jar.conf
- ./platform/nginx-java-21/docker/config/supervisor/conf.d/java-dev.conf

### 4. Build the container

**REMEMBER:** Set the container core configuration files at `./docker/config/nginx/conf.d/default.conf`

Once the variables required by `docker-compose.yml` file, build the container
```sh
$ make set # set the API port to be binded to container port 80 - e.g. your API request at: http://127.0.0.1:7540
$ make create # local development version
```

After building the container, you can checkout its main information
```sh
$ make info
MY PROJECT: NGINX - JAVA 21
Container ID.: de62596bfeb8
Name.........: javasb-jar-apirest-dev
Image........: javasb-jar-apirest-dev:alpine3.22-nginx1.28-java21
Memory.......: 512M
Host.........: 127.0.0.1:7501
Hostname.....: 192.168.1.41:7501
Docker.Host..: 172.20.0.2
NetworkID....: 142897d77adf48319c5cb5113b59023ede7a6a2fe58d0d2e0193aeac08f9df1f
```

This Platform Repository does not provide SSL as it is part of an upper infrastructure layer. But if you use SSL you will have to proxy to the selected API port
<br>

### 4. ## Run Migrations

It is important to run the migration before the API is up and running
```bash
/var/www $ mvn liquibase:update
```
<br>

### 5. Platform Content

O.S. Linux
```bash
/var/www $ cat /etc/os-release
NAME="Alpine Linux"
ID=alpine
VERSION_ID=3.23.3
PRETTY_NAME="Alpine Linux v3.23"
HOME_URL="https://alpinelinux.org/"
BUG_REPORT_URL="https://gitlab.alpinelinux.org/alpine/aports/-/issues"
```

NginxServer Platform NGINX
```bash
/var/www $ nginx -v
nginx version: nginx/1.28.2
```

Java
```bash
/var/www $ java -version
openjdk version "21.0.10" 2026-01-20 LTS
OpenJDK Runtime Environment Temurin-21.0.10+7 (build 21.0.10+7-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.10+7 (build 21.0.10+7-LTS, mixed mode, sharing)
```

Maven
```bash
$ mvn --version
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: /usr/share/java/maven-3
Java version: 21.0.10, vendor: Eclipse Adoptium, runtime: /opt/java/openjdk
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "6.12.38+kali-amd64", arch: "amd64", family: "unix"
```

### 6. Start the API service

On building the container, both services wont be able to start. Access into the container to start them up. Take into account that they do not immediatly start at your browser for compiling time

Access into container
```bash
$ make apirest-ssh
```

Main or latest API version that nginx proxy to app.jar file running on port 8080
```bash
/var/www $ supervisorctl start java-jar
```

Local development version on port 8081 - `$ mvn spring-boot:run` - use `$ make apirest-info` to discover docker assigned port
```bash
/var/www $ supervisorctl start java-dev
```

## Development

Since the container is using Supervisor, Supervisor is the "boss" of the API process. You should never try to kill a Supervisor-managed process manually or with Maven, because Supervisor will think it crashed! Avoid the "Supervisor Trap" (Foreground vs. Background) as it has one very strict rule: It only manages programs that run in the foreground. It will mark the process as `FATAL` or `EXITED` and the Java app is still secretly running in the background as a "zombie" process.

- mvn spring-boot:run runs in the foreground. It blocks the terminal and streams the logs continuously. Supervisor loves this because it can hold onto the process, read the logs, and know exactly when it stops.

- mvn spring-boot:start is designed to start the app in the background (daemon mode) and immediately return control of the terminal to you.

It is not a big problem, but it is important to avoid destroying and building the container continuosly.

As supervisor manage `$ mvn spring-boot:run` you can stop that service
```bash
/var/www $ supervisorctl stop java-dev
```

Then, you can use if needed
```bash
/var/www $ mvn spring-boot:start
/var/www $ mvn spring-boot:stop

# Or just
/var/www $ mvn spring-boot:run
```

### When are start and stop actually used?

In the professional Java world, spring-boot:start and spring-boot:stop are almost exclusively used for Automated Integration Testing inside the pom.xml, not for running the actual development server.

#### A CI/CD pipeline (like GitHub Actions) will use it like this:

- `mvn spring-boot:start` (Starts the app in the background)
- `mvn failsafe:integration-test` (Fires HTTP requests at the background app to test it)
- `mvn spring-boot:stop` (Kills the background app so the pipeline can finish)


## Migrations

```bash
/var/www $ mvn liquibase:update
```
<br><br>

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

## Create the Application / REST API

Access into container to create the Application JAR file
```sh
$ make ssh

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

- Test:
 Visit http://127.0.0.1:[main-platform-port-set]/

All toghether:
```bash
/var/www $ mvn clean package && cp target/api-springboot-0.0.1-SNAPSHOT.jar target/app.jar && supervisorctl -c /etc/supervisor/supervisord.conf reload
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
$ make apirest-ssh
```

Testing
```bash
# Run all tests
/var/www/ $ mvn test

# Run only a specific class
/var/www/ $ mvn test -Dtest=JwtServiceTest

# Run with verbose output
/var/www/ $ mvn test -Dsurefire.reportFormat=plain
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



<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>