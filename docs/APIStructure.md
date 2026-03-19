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
./src
├── Application # Application layer (Use Cases / Commands / Queries / Handlers)
│   ├── Admin
│   │   ├── Command
│   │   │   ├── IncrementAccessLogRequestCountCommand.php
│   │   │   ├── IncrementAccessLogRequestCountHandler.php
│   │   │   ├── TerminateAccessLogCommand.php
│   │   │   ├── TerminateAccessLogHandler.php
│   │   │   ├── TerminateAllUserAccessLogsCommand.php
│   │   │   ├── TerminateAllUserAccessLogsHandler.php
│   │   │   ├── UpdateAdminProfileCommand.php
│   │   │   └── UpdateAdminProfileHandler.php
│   │   ├── DTO
│   │   │   ├── AdminAccessLogDTO.php
│   │   │   ├── AdminDTO.php
│   │   │   └── AdminProfileDTO.php
│   │   ├── Query
│   │   │   ├── GetAdminAccessLogByIdHandler.php
│   │   │   ├── GetAdminAccessLogByIdQuery.php
│   │   │   ├── GetAdminAccessLogByTokenHandler.php
│   │   │   ├── GetAdminAccessLogByTokenQuery.php
│   │   │   ├── GetAdminAccessLogsHandler.php
│   │   │   ├── GetAdminAccessLogsQuery.php
│   │   │   ├── GetAdminProfileHandler.php
│   │   │   ├── GetAdminProfileQuery.php
│   │   │   ├── GetAllAdminAccessLogsHandler.php
│   │   │   ├── GetAllAdminAccessLogsQuery.php
│   │   │   ├── GetAllAdminHandler.php
│   │   │   └── GetAllAdminQuery.php
│   │   └── Service
│   ├── Employee
│   │   ├── Command
│   │   │   ├── IncrementAccessLogRequestCountCommand.php
│   │   │   ├── IncrementAccessLogRequestCountHandler.php
│   │   │   ├── TerminateAccessLogCommand.php
│   │   │   ├── TerminateAccessLogHandler.php
│   │   │   ├── TerminateAllUserAccessLogsCommand.php
│   │   │   ├── TerminateAllUserAccessLogsHandler.php
│   │   │   ├── UpdateEmployeeProfileCommand.php
│   │   │   └── UpdateEmployeeProfileHandler.php
│   │   ├── DTO
│   │   │   ├── EmployeGeoLocationDTO.php
│   │   │   ├── EmployeeAccessLogDTO.php
│   │   │   ├── EmployeeContactDTO.php
│   │   │   ├── EmployeeDTO.php
│   │   │   ├── EmployeeFullDTO.php
│   │   │   ├── EmployeeProfileDTO.php
│   │   │   └── EmployeeWorkplaceDTO.php
│   │   ├── Query
│   │   │   ├── GetAllEmployeeAccessLogsHandler.php
│   │   │   ├── GetAllEmployeeAccessLogsQuery.php
│   │   │   ├── GetAllEmployeesHandler.php
│   │   │   ├── GetAllEmployeesQuery.php
│   │   │   ├── GetEmployeeAccessLogByIdHandler.php
│   │   │   ├── GetEmployeeAccessLogByIdQuery.php
│   │   │   ├── GetEmployeeAccessLogByTokenHandler.php
│   │   │   ├── GetEmployeeAccessLogByTokenQuery.php
│   │   │   ├── GetEmployeeAccessLogsHandler.php
│   │   │   ├── GetEmployeeAccessLogsQuery.php
│   │   │   ├── GetEmployeeByIdHandler.php
│   │   │   ├── GetEmployeeByIdQuery.php
│   │   │   ├── GetEmployeeByUserIdHandler.php
│   │   │   ├── GetEmployeeByUserIdQuery.php
│   │   │   ├── GetEmployeeProfileHandler.php
│   │   │   └── GetEmployeeProfileQuery.php
│   │   └── Service
│   │       ├── EmployeeAggregateService.php
│   │       └── EmployeeAuthenticationService.php
│   ├── Employment
│   │   ├── Command
│   │   │   ├── AddExtraHoursCommand.php
│   │   │   ├── AddExtraHoursHandler.php
│   │   │   ├── ClockInCommand.php
│   │   │   ├── ClockInHandler.php
│   │   │   ├── ClockOutCommand.php
│   │   │   ├── ClockOutHandler.php
│   │   │   ├── CorrectClockingHandler.php
│   │   │   ├── CorrectCockingCommand.php
│   │   │   ├── DeleteWorkdayCommand.php
│   │   │   ├── DeleteWorkdayHandler.php
│   │   │   ├── MarkContractSignedCommand.php
│   │   │   ├── MarkContractSignedHandler.php
│   │   │   ├── UpdateEmploymentContractCommand.php
│   │   │   └── UpdateEmploymentContractHandler.php
│   │   ├── DTO
│   │   │   ├── AuditLogDTO.php
│   │   │   ├── EmploymentContractDTO.php
│   │   │   ├── EmploymentContractTypeDTO.php
│   │   │   ├── EmploymentWorkdayClockingDTO.php
│   │   │   └── EmploymentWorkdayDTO.php
│   │   ├── Query
│   │   │   ├── GetAllContractTypesHandler.php
│   │   │   ├── GetAllContractTypesQuery.php
│   │   │   ├── GetClockingsByUserHandler.php
│   │   │   ├── GetClockingsByUserQuery.php
│   │   │   ├── GetClockingsByWorkdayHandler.php
│   │   │   ├── GetClockingsByWorkdayQuery.php
│   │   │   ├── GetContractAuditTrailHandler.php
│   │   │   ├── GetContractByIdHandler.php
│   │   │   ├── GetContractByIdQuery.php
│   │   │   ├── GetContractTypeByIdHandler.php
│   │   │   ├── GetContractTypeByIdQuery.php
│   │   │   ├── GetContractsByUserHandler.php
│   │   │   ├── GetContractsByUserQuery.php
│   │   │   ├── GetWorkdayByIdHandler.php
│   │   │   ├── GetWorkdayByIdQuery.php
│   │   │   ├── GetWorkdaysByUserHandler.php
│   │   │   └── GetWorkdaysByUserQuery.php
│   │   └── Service
│   │       └── EmploymentAuditService.php
│   ├── Geo
│   │   ├── Command
│   │   ├── DTO
│   │   │   └── GeoLocationDTO.php
│   │   ├── Query
│   │   └── Service
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
│   │   ├── Query
│   │   │   ├── GetAllMasterAccessLogsHandler.php
│   │   │   ├── GetAllMasterAccessLogsQuery.php
│   │   │   ├── GetAllMasterHandler.php
│   │   │   ├── GetAllMasterQuery.php
│   │   │   ├── GetMasterAccessLogByIdHandler.php
│   │   │   ├── GetMasterAccessLogByIdQuery.php
│   │   │   ├── GetMasterAccessLogByTokenHandler.php
│   │   │   ├── GetMasterAccessLogByTokenQuery.php
│   │   │   ├── GetMasterAccessLogsHandler.php
│   │   │   ├── GetMasterAccessLogsQuery.php
│   │   │   ├── GetMasterProfileHandler.php
│   │   │   └── GetMasterProfileQuery.php
│   │   └── Service
│   ├── Office
│   │   ├── Command
│   │   ├── DTO
│   │   │   ├── DepartmentDTO.php
│   │   │   └── JobDTO.php
│   │   ├── Query
│   │   │   ├── GetAllDepartmentsHandler.php
│   │   │   ├── GetAllDepartmentsQuery.php
│   │   │   ├── GetDepartmentByIdHandler.php
│   │   │   ├── GetDepartmentByIdQuery.php
│   │   │   ├── GetJobByIdHandler.php
│   │   │   ├── GetJobByIdQuery.php
│   │   │   ├── GetJobsByDepartmentHandler.php
│   │   │   └── GetJobsByDepartmentQuery.php
│   │   └── Service
│   ├── Shared # Shared Domain (cross-domain)
│   │   ├── DTO
│   │   │   ├── PaginatedResultDTO.php
│   │   │   └── PaginationDTO.php
│   │   └── Service
│   └── User
│       ├── Command
│       │   ├── LoginCommand.php
│       │   ├── LoginHandler.php
│       │   ├── RegisterAdminCommand.php
│       │   ├── RegisterAdminHandler.php
│       │   ├── RegisterEmployeeCommand.php
│       │   ├── RegisterEmployeeHandler.php
│       │   ├── RegisterMasterCommand.php
│       │   └── RegisterMasterHandler.php
│       ├── DTO
│       │   └── UserDTO.php
│       ├── Query
│       │   ├── GetAllUsersHandler.php
│       │   ├── GetAllUsersQuery.php
│       │   ├── GetUserByIdHandler.php
│       │   └── GetUserByIdQuery.php
│       └── Service
├── DataFixtures # Kept at root (Symfony convention)
│   ├── AppFixtures.php
│   ├── EmploymentGroupFixture.php
│   ├── GeoGroupFixtures.php
│   └── UsersGroupFixtures.php
├── Domain # Domain layer (Entities, Value Objects, Domain Services, Interfaces)
│   ├── Admin
│   │   ├── Entity
│   │   │   ├── Admin.php
│   │   │   ├── AdminAccessLog.php
│   │   │   └── AdminProfile.php
│   │   ├── Event
│   │   │   ├── AdminCreatedEvent.php
│   │   │   └── AdminLoggedInEvent.php
│   │   ├── Fixture
│   │   │   └── AdminFixtures.php
│   │   ├── Repository # Interface only
│   │   │   ├── AdminAccessLogRepositoryInterface.php
│   │   │   ├── AdminProfileRepositoryInterface.php
│   │   │   └── AdminRepositoryInterface.php
│   │   ├── Service # Domain logi
│   │   │   └── AdminAuthenticationService.php
│   │   └── ValueObject
│   ├── Employee
│   │   ├── Entity
│   │   │   ├── Employee.php
│   │   │   ├── EmployeeAccessLog.php
│   │   │   ├── EmployeeContact.php
│   │   │   ├── EmployeeGeoLocation.php
│   │   │   ├── EmployeeProfile.php
│   │   │   └── EmployeeWorkplace.php
│   │   ├── Event
│   │   │   ├── EmployeeCreatedEvent.php
│   │   │   └── EmployeeLoggedInEvent.php
│   │   ├── Fixture
│   │   │   └── EmployeeFixtures.php
│   │   ├── Repository
│   │   │   ├── EmployeeAccessLogRepositoryInterface.php
│   │   │   ├── EmployeeContactRepositoryInterface.php
│   │   │   ├── EmployeeGeoLocationRepositoryInterface.php
│   │   │   ├── EmployeeProfileRepositoryInterface.php
│   │   │   ├── EmployeeRepositoryInterface.php
│   │   │   └── EmployeeWorkplaceRepositoryInterface.php
│   │   ├── Service
│   │   │   └── EmployeeEligibilityChecker.php
│   │   └── ValueObject
│   ├── Employment
│   │   ├── Entity
│   │   │   ├── EmploymentContract.php
│   │   │   ├── EmploymentContractLog.php
│   │   │   ├── EmploymentContractType.php
│   │   │   ├── EmploymentWorkday.php
│   │   │   ├── EmploymentWorkdayClocking.php
│   │   │   ├── EmploymentWorkdayClockingLog.php
│   │   │   └── EmploymentWorkdayLog.php
│   │   ├── Event
│   │   ├── Fixture
│   │   │   └── EmploymentFixtures.php
│   │   ├── Repository
│   │   │   ├── EmploymentContractRepositoryInterface.php
│   │   │   ├── EmploymentContractTypeRepositoryInterface.php
│   │   │   ├── EmploymentWorkdayClockingRepositoryInterface.php
│   │   │   └── EmploymentWorkdayRepositoryInterface.php
│   │   ├── Service
│   │   └── ValueObject
│   │       └── EmploymentActionKey.php
│   ├── Geo
│   │   ├── Entity
│   │   │   └── GeoLocation.php
│   │   ├── Event
│   │   ├── Fixture
│   │   │   └── GeoLocationFixtures.php
│   │   └── Repository
│   │       └── GeoLocationRepositoryInterface.php
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
│   ├── Office
│   │   ├── Entity
│   │   │   ├── Department.php
│   │   │   └── Job.php
│   │   ├── Event
│   │   ├── Fixture
│   │   │   └── OfficeFixtures.php
│   │   ├── Repository
│   │   │   ├── DepartmentRepositoryInterface.php
│   │   │   └── JobRepositoryInterface.php
│   │   └── ValueObject
│   ├── Shared
│   │   ├── Exception
│   │   │   ├── DomainException.php
│   │   │   ├── EntityNotFoundException.php
│   │   │   ├── InvalidEmailException.php
│   │   │   ├── InvalidUuidException.php
│   │   │   └── ValidationException.php
│   │   └── ValueObject
│   │       ├── DateTimeVO.php
│   │       ├── Email.php
│   │       └── Uuid.php
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
├── Infrastructure # Infrastructure layer (Adapters: Framework adapters, Persistence, External services, Messaging)
│   ├── Event
│   │   ├── Listener
│   │   │   └── JWTCreatedListener.php
│   │   └── Subscriber
│   │       ├── ApiExceptionSubscriber.php
│   │       └── DomainExceptionSubscriber.php
│   ├── Http
│   │   └── ArgumentResolver.php
│   ├── Mail # implements Domain service or Application port
│   │   └── UserRegisterMail.php
│   ├── Messaging
│   │   ├── Handler
│   │   │   └── NotifyUserMessageHandler.php
│   │   └── Message
│   │       └── NotifyUserMessage.php
│   ├── Persistence
│   │   ├── Doctrine
│   │   │   ├── Mapping # Optional: if using XML/YAML instead of annotations
│   │   │   └── Repository
│   │   │       ├── Admin
│   │   │       │   ├── AdminAccessLogRepository.php
│   │   │       │   ├── AdminProfileRepository.php
│   │   │       │   └── AdminRepository.php
│   │   │       ├── Employee
│   │   │       │   ├── EmployeeAccessLogRepository.php
│   │   │       │   ├── EmployeeProfileRepository.php
│   │   │       │   └── EmployeeRepository.php
│   │   │       ├── Employment
│   │   │       │   ├── EmploymentContractRepository.php
│   │   │       │   ├── EmploymentContractTypeRepository.php
│   │   │       │   ├── EmploymentWorkdayClockingRepository.php
│   │   │       │   └── EmploymentWorkdayRepository.php
│   │   │       ├── Geo
│   │   │       │   └── GeoLocationRepository.php
│   │   │       ├── Master
│   │   │       │   ├── MasterAccessLogRepository.php
│   │   │       │   ├── MasterProfileRepository.php
│   │   │       │   └── MasterRepository.php
│   │   │       ├── Office
│   │   │       │   ├── DepartmentRepository.php
│   │   │       │   └── JobRepository.php
│   │   │       └── User
│   │   │           └── UserRepository.php
│   │   ├── MongoDB
│   │   └── Redis
│   ├── Security
│   │   ├── ApiAccessDeniedHandler.php
│   │   ├── CustomAuthenticationSuccessHandler.php
│   │   └── JwtAuthenticationEntryPoint.php
│   └── Service
│       ├── MongoDBService.php
│       └── RedisService.php
├── Kernel.php
└── Presentation # Presentation layer (Controllers, API endpoints, CLI, GraphQL resolvers)
    ├── Cli
    │   └── Command
    ├── Http
    │   ├── GraphQl # Open to GraphQL
    │   │   └── Resolver
    │   └── Rest
    │       ├── AbstractApiController.php
    │       ├── Admin
    │       │   ├── AdminAccountController.php
    │       │   ├── AdminAuthController.php
    │       │   ├── AdminForAdminsController.php
    │       │   ├── AdminForEmployeesController.php
    │       │   └── AdminUsersController.php
    │       ├── Employee
    │       │   ├── EmployeeAccountController.php
    │       │   ├── EmployeeAuthController.php
    │       │   └── Employment
    │       │       └── EmployeeWorkdayController.php
    │       ├── Employment
    │       │   ├── EmploymentClockingController.php
    │       │   ├── EmploymentContractController.php
    │       │   └── EmploymentWorkdayController.php
    │       ├── Geo
    │       │   └── GeoController.php
    │       ├── Master
    │       │   ├── MasterAccountController.php
    │       │   ├── MasterAuthController.php
    │       │   ├── MasterForAdminsController.php
    │       │   ├── MasterForEmployeesController.php
    │       │   └── MasterUsersController.php
    │       ├── Office
    │       ├── ServicesTestController.php
    │       └── User
    │           └── UserAuthController.php
    └── Request # Request DTOs for validation
        ├── Admin
        │   ├── CreateAdminRequest.php
        │   ├── CreateEmployeeRequest.php
        │   ├── UpdateAdminProfileRequest.php
        │   └── UpdateEmployeeProfileRequest.php
        ├── BaseRequest.php
        ├── Employee
        │   └── UpdateEmployeeProfileRequest.php
        ├── Employment
        ├── Geo
        ├── Master
        │   ├── CreateMasterRequest.php
        │   └── UpdateMasterProfileRequest.php
        ├── Office
        └── ValidatableRequestTrait.php

137 directories, 276 files

# Final folder structure summary
src/
├── Application/          # Use cases (Commands/Queries + Handlers)
├── Domain/               # Pure business logic (Entities, VOs, Interfaces, Domain Services)
├── Infrastructure/       # Adapters (Doctrine repos, Mailer, Redis, Messaging)
├── Presentation/         # Controllers, CLI
├── DataFixtures/         # Fixtures
└── Kernel.php            # Symfony
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>