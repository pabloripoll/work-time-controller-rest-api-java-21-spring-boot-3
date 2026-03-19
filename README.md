<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./src/main/resources/static/files/images/pr-banner-long.png">
</div>

# SOCIAL FEED - JAVA 17 / MAVEN - SPRINGBOOT 3

This repository contains a basic example of a RESTful API service built with **Springboot 3**, intended for research purposes and as a demonstration of my developer profile. It implements the core features of a minimal, custom social feed application and serves as a reference project for learning, experimentation, or as a back-end development code sample.

> ⚠️ **Project Status: In Development**
>
> This repository is currently under active development and not yet ready for use. Features and APIs may change, and breaking changes can occur. Please, be aware that the codebase is actively evolving.
<br><br>

## Usage

First start up or re-install dependencies
```bash
$ mvn -U clean package
```

Start REST API
```bash
$ mvn spring-boot:run
```

Tests
```bash
/var/www $ mvn -U clean test
```
<br><br>

## Directories Structure Design

Mixed between **Feature-Based Modular Structure** *(Vertical Slice)* and **Hexagonal Architecture** *(Ports & Adapters / Clean Architecture)*

HTTP routes/controllers in a separate package like com.restapi.http (outside com.restapi.domain) is a fine and common approach (it’s what hexagonal/ports-and-adapters and typical layered DDD recommend). Keep domain types and rules inside domain packages and make the http package an adapter (only depends on application/use-case services, not the domain implementation internals).

Suggested project layout (feature + layer separation)
```bash
src/main/java/com/restapi
├── domain
│   ├── user
│   │   ├── controller
│   │   ├── entity
│   │   ├── repository
│   │   ├── dto
│   │   ├── service
│   .   └── usecase (use case interfaces)
│
├── infrastructure
│   ├── sockets
│   ├── messenger
│   └── storage
│
└── adapter
    ├── console
    │   └── ...
    │
    └── http
        ├── exception (http exception handlers, mappers)
        ├── security
        ├── web
        └── rest
            ├── auth
            │   └── AuthRoute.java
            ├── account
            │   └── AccountMemberRoute.java
            ├── admin
            │   └── AdminDashboardRoute.java
            ├── feed
            │   └── PostRoute.java
            └── members
                └── ProfileRoute.java
```

### Why this approach?

- Single direction of dependencies: http -> application(use-cases) -> domain -> infrastructure (adapters).
- The http package stays an adapter: it only translates HTTP to DTOs and calls use-cases. It should not contain business rules or domain entities.
- Easier testing: controllers can be unit tested by mocking services; domain logic can be tested in isolation.
- Clear separation for teams: backend devs working on domain vs API wiring are separated.

### Patterns and rules to follow

- Controllers/Routes should accept/return DTOs (do not expose domain entities directly).
- Controllers depend on application/use-case interfaces (not on repositories).
- Keep mapping logic (DTO <-> domain) in a mapper class or in the application layer.
- Validation: validate incoming DTOs at the boundary (HTTP) using validators (e.g., javax validation).
- Error handling: a global exception handler maps domain exceptions to HTTP responses.
- Security: implement authentication/authorization as middleware/filters in http or infrastructure layer, not in domain.
- Versioning: put /v1/ in route paths or package names if you plan public versioning.
- Avoid CREATE TABLE IF EXISTS style workarounds for schema drift — keep DB migrations authoritative.
<br><br>

## Contributing

Contributions are very welcome! Please open issues or submit PRs for improvements, new features, or bug fixes.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'feat: Add new feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Create a new Pull Request
<br><br>

## License

This project is open-sourced under the [MIT license](LICENSE).

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./src/main/resources/static/files/images/pr-banner-long.png">
</div>