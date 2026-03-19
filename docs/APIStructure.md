<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# DDD + Hexagonal Architecture

DDD organizes your domain logic, Hexagonal keeps it isolated from frameworks/databases!

- DDD provides the "what" (business concepts)
- Hexagonal provides the "where" (architectural layers)

## Hexagonal Architecture (Ports & Adapters)

Defines the layer separation:

- Domain (Center) - Pure business logic, no dependencies
- Application - Use cases, orchestrates domain
- Infrastructure (Adapters) - External integrations (DB, APIs)
- Presentation (Adapters) - User interfaces (REST, GraphQL)

## DDD (Domain-Driven Design)

Defines how to organize business logic inside those layers:

- Entities, Value Objects, Aggregates
- Domain Services
- Repositories (interfaces)
- Ubiquitous Language

## 🎯 Directory Purposes

| Layer           | Purpose                          | Examples                                                 |
| --------------- | -------------------------------- | -------------------------------------------------------- |
| Domain	      | Business rules, entities, logic	 |  Employee, Contract, Email (Value Object)                |
| Application	  | Use cases, orchestration	     |  Command/Query Handlers, DTOs, Services                  |
| Infrastructure  | Technical implementation	     |  Doctrine repositories, HTTP clients, external APIs      |
| Presentation	  | User interaction	             |  REST controllers, CLI commands, GraphQL resolvers       |
<br>

## 📁 Project Structure

**Proposed structure desing overview:**
```bash
┌───────────────────────────────────────────────────────────────────┐
│                        DOMAIN (Hexagon)                           │
│                                                                   │
│  Master.java, MasterProfile.java        ← Domain Model            │
│  MasterRepository (interface)           ← Output PORT             │
│  PasswordHashingService (interface)     ← Output PORT             │
└───────────────────┬─────────────────────────────┬─────────────────┘
                    │                             │
          implements│                   implements│
                    ▼                             ▼
┌───────────────────────────────┐   ┌───────────────────────────────┐
│  MasterRepositoryAdapter      │   │  BcryptPasswordHashingService │
│  (Infrastructure — DB)        │   │  (Infrastructure — Security)  │
│                               │   └───────────────────────────────┘
│  uses MasterJpaRepository     │
│  uses MasterJpaMapper         │
└───────────────────────────────┘

┌───────────────────────────────────────────────────────────────────┐
│                     APPLICATION LAYER                             │
│                                                                   │
│  CreateMasterUseCase          ← orchestrates domain               │
│  GetMasterByIdUseCase         ← reads via Output Port             │
└───────────────────┬───────────────────────────────────────────────┘
                    │ called by
                    ▼
┌───────────────────────────────────────────────────────────────────┐
│                   PRESENTATION LAYER                              │
│                                                                   │
│  MasterController             ← Input ADAPTER (REST)              │
│  (calls use cases directly)                                       │
└───────────────────────────────────────────────────────────────────┘

# Final folder structure summary
src/main/java/api/dev/
├── Application/          # Use cases (Commands/Queries + Handlers)
├── Domain/               # Pure business logic (Entities, VOs, Interfaces, Domain Services)
├── Infrastructure/       # Adapters (Doctrine repos, Mailer, Redis, Messaging)
└── Presentation/         # Controllers, CLI
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>