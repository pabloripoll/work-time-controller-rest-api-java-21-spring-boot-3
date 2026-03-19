<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./../images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# Domain Master

```bash
# Master Domain
# Domain that serves Webmaster users to manage and maintain its related tasks.

$ tree ./src ./tests
./src
├── Application
│   ├── Master
│   │   ├── Command
│   │   │   ├── IncrementAccessLogRequestCountCommand.php
│   │   │   ├── IncrementAccessLogRequestCountHandler.php
│   │   │   ├── TerminateAccessLogCommand.php
│   │   │   ├── TerminateAccessLogHandler.php
│   │   │   ├── TerminateAllUserAccessLogsCommand.php
│   │   │   ├── TerminateAllUserAccessLogsHandler.php
│   │   │   ├── UpdateMasterProfileCommand.php
│   │   │   └── UpdateMasterProfileHandler.php
│   │   ├── DTO
│   │   │   ├── MasterAccessLogDTO.php
│   │   │   ├── MasterDTO.php
│   │   │   └── MasterProfileDTO.php
│   │   └── Query
│   │       ├── GetAllMasterAccessLogsHandler.php
│   │       ├── GetAllMasterAccessLogsQuery.php
│   │       ├── GetMasterAccessLogByIdHandler.php
│   │       ├── GetMasterAccessLogByIdQuery.php
│   │       ├── GetMasterAccessLogByTokenHandler.php
│   │       ├── GetMasterAccessLogByTokenQuery.php
│   │       ├── GetMasterAccessLogsHandler.php
│   │       ├── GetMasterAccessLogsQuery.php
│   │       ├── GetMasterProfileHandler.php
│   │       └── GetMasterProfileQuery.php
│   └── User
│       ├── Command
│       │   ├── LoginCommand.php
│       │   ├── LoginHandler.php
│       │   ├── RegisterAdminCommand.php
│       │   ├── RegisterAdminHandler.php
│       │   ├── RegisterEmployeeCommand.php
│       │   └── RegisterEmployeeHandler.php
│       ├── DTO
│       │   └── UserDTO.php
│       └── Query
│           ├── GetAllUsersHandler.php
│           ├── GetAllUsersQuery.php
│           ├── GetUserByIdHandler.php
│           └── GetUserByIdQuery.php
│
├── DataFixtures
│   ├── AppFixtures.php
│   └── UsersGroupFixture.php
│
├── Domain
│   ├── Master
│   │   ├── Entity
│   │   │   ├── Master.php
│   │   │   ├── MasterAccessLog.php
│   │   │   └── MasterProfile.php
│   │   ├── Event
│   │   │   ├── MasterCreatedEvent.php
│   │   │   └── MasterLoggedInEvent.php
│   │   ├── Fixture
│   │   │   └── MasterFixtures.php
│   │   ├── Repository
│   │   │   ├── MasterAccessLogRepositoryInterface.php
│   │   │   ├── MasterProfileRepositoryInterface.php
│   │   │   └── MasterRepositoryInterface.php
│   │   ├── Service
│   │   │   └── MasterAuthenticationService.php
│   │   └── ValueObject
│   │       └── MasterDTO.php
│   │
│   └── User
│       ├── Entity
│       │   └── User.php
│       ├── Event
│       │   ├── UserCreatedEvent.php
│       │   └── UserLoggedInEvent.php
│       ├── Fixture
│       ├── Repository
│       │   └── UserRepositoryInterface.php
│       ├── Service
│       │   └── UserAuthenticationService.php
│       └── ValueObject
│           └── UserRole.php
│
├── Infrastructure
│   ├── Event
│   │   ├── Listener
│   │   │   └── JWTCreatedListener.php
│   │   └── Subscriber
│   │       ├── ApiExceptionSubscriber.php
│   │       └── DomainExceptionSubscriber.php
│   ├── Persistence
│   │   ├── Doctrine
│   │   │   ├── Mapping
│   │   │   └── Repository
│   │   │       ├── Master
│   │   │       │   ├── MasterAccessLogRepository.php
│   │   │       │   ├── MasterProfileRepository.php
│   │   │       │   └── MasterRepository.php
│   │   │       .
│   │   .
│   │
│   ├── Security
│   │   ├── ApiAccessDeniedHandler.php
│   │   ├── CustomAuthenticationSuccessHandler.php
│   │   └── JwtAuthenticationEntryPoint.php
│   .
│
└── Presentation
    ├── Cli
    │   └── Command
    │
    ├── Http
    │   └── Rest
    │       ├── AbstractApiController.php
    │       ├── Master
    │       │   ├── MasterAdminsController.php
    │       │   ├── MasterAuthController.php
    │       │   ├── MasterEmployeesController.php
    │       │   ├── MasterProfileController.php
    │       │   └── MasterUsersController.php
    │       └── User
    │           └── UserAuthController.php
    └── Request
        ├── Master
        │   ├── CreateAdminRequest.php
        │   └── UpdateMasterProfileRequest.php
        └── ValidatableRequestTrait.php
./tests
├── Functional
│   └── Presentation
│       └── Http
│           └── Rest
│               ├── Master
│               │   └── MasterAuthControllerTest.php
│               .
│
├── Integration
│   ├── Infrastructure
│   │   └── Persistence
│   │       └── Doctrine
│   │           └── Repository
│   │               └── UserRepositoryTest.php
│   │
│   └── Presentation
│       └── Http
│           └── Rest
│
├── Support
│   └── Factory
│       └── UserFactory.php
├── Unit
│   └── Domain
│       ├── Master
│       ├── Shared
│       │   └── ValueObject
│       │       ├── EmailTest.php
│       │       └── UuidTest.php
│       ├── User
│       │   └── Entity
│       │       └── UserTest.php
.       .
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./../images/pr-banner-long.png">
</div>