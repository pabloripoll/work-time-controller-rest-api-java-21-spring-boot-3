<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./../images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# Domain Layer Services vs Application Layer Services

🚧 ROADMAP

## Domain Services (App\Domain\Employee\Service)

Purpose: Encapsulate business logic that doesn't naturally fit in a single entity

Characteristics:

- Contains pure business rules
- Domain-specific operations
- No infrastructure dependencies (no repositories, no HTTP, no DB)
- Operates on domain entities
- Stateless business logic

Examples:
```php
namespace App\Domain\Employee\Service;

// CORRECT - Domain Service
class EmployeeSalaryCalculator
{
    public function calculateAnnualSalary(Employee $employee, Contract $contract): Money
    {
        // Pure business logic
        $baseSalary = $contract->getMonthlySalary();
        $bonus = $this->calculateBonus($employee);
        return $baseSalary->multiply(12)->add($bonus);
    }

    private function calculateBonus(Employee $employee): Money
    {
        // Business rules for bonus calculation
    }
}

// CORRECT - Domain Service
class EmployeePromotionPolicy
{
    public function canBePromoted(Employee $employee): bool
    {
        // Business rules: tenure, performance, etc.
        return $employee->getTenureYears() >= 2
            && !$employee->isBanned();
    }
}
```

## Application Services (App\Application\Employee\Service)

Purpose: Orchestrate domain objects and infrastructure to fulfill use cases

Characteristics:

- Coordinates between multiple repositories
- Orchestrates workflows
- Can inject repositories, external services
- Handles cross-cutting concerns (transactions, logging)
- Maps domain models to DTOs

Examples:
```php
namespace App\Application\Employee\Service;

// CORRECT - Application Service
readonly class EmployeeAggregateService
{
    public function __construct(
        private EmployeeRepositoryInterface $employeeRepo,
        private EmployeeProfileRepositoryInterface $profileRepo,
        private EmployeeContactsRepositoryInterface $contactsRepo,
        private EmployeeWorkplaceRepositoryInterface $workplaceRepo,
        private EmployeeGeoLocationRepositoryInterface $geoLocationRepo
    ) {
    }

    public function getFullEmployee(int $employeeId): ?EmployeeFullDTO
    {
        // Orchestrates multiple repositories
        $employee = $this->employeeRepo->findById($employeeId);
        $profile = $this->profileRepo->findByEmployeeId($employeeId);
        // ... fetch from multiple sources

        return new EmployeeFullDTO(...);
    }
}

// CORRECT - Application Service
readonly class EmployeeOnboardingService
{
    public function __construct(
        private EmployeeRepositoryInterface $employeeRepo,
        private UserRepositoryInterface $userRepo,
        private EmailService $emailService,
        private EventDispatcher $eventDispatcher
    ) {
    }

    public function onboardNewEmployee(CreateEmployeeCommand $command): Employee
    {
        // Orchestrates multiple operations
        $user = $this->userRepo->create(...);
        $employee = Employee::create(...);
        $this->employeeRepo->save($employee);

        // Infrastructure concerns
        $this->emailService->sendWelcomeEmail($user);
        $this->eventDispatcher->dispatch(new EmployeeCreated($employee));

        return $employee;
    }
}
```

## Decision Tree

```sh
Is it PURE business logic that operates on entities?
│
├─ YES Domain Service (App\Domain\Employee\Service)
│   Examples:
│   - EmployeeSalaryCalculator
│   - EmployeePromotionPolicy
│   - EmployeeEligibilityChecker
│
└─ NO → Does it coordinate repositories/infrastructure?
    │
    └─ YES → Application Service (App\Application\Employee\Service)
        Examples:
        - EmployeeAggregateService (fetches from multiple repos)
        - EmployeeOnboardingService (orchestrates workflow)
        - EmployeeReportGenerator (queries + formatting)
```

## Real-World Examples

### Domain Service (belongs in Domain\)
```php
namespace App\Domain\Employee\Service;

class EmployeeTransferPolicy
{
    /**
     * Pure business logic - can this employee transfer departments?
     */
    public function canTransferToDepartment(
        Employee $employee,
        Department $targetDepartment
    ): bool {
        // Business rules only, no repository calls
        if ($employee->isBanned()) {
            return false;
        }

        if ($employee->getTenureMonths() < 6) {
            return false;
        }

        if ($targetDepartment->isAtCapacity()) {
            return false;
        }

        return true;
    }
}
```

### Application Service (belongs in Application\)

```php
namespace App\Application\Employee\Service;

readonly class EmployeeTransferService
{
    public function __construct(
        private EmployeeRepositoryInterface $employeeRepo,
        private EmployeeWorkplaceRepositoryInterface $workplaceRepo,
        private DepartmentRepositoryInterface $departmentRepo,
        private EmployeeTransferPolicy $transferPolicy, // ← Injects domain service
        private EventDispatcher $eventDispatcher
    ) {
    }

    /**
     * Orchestrates the transfer process
     */
    public function transferEmployee(
        int $employeeId,
        int $targetDepartmentId
    ): void {
        // Fetch from repos
        $employee = $this->employeeRepo->findById($employeeId);
        $targetDept = $this->departmentRepo->findById($targetDepartmentId);
        $workplace = $this->workplaceRepo->findByEmployeeId($employeeId);

        // Use domain service for business rule
        if (! $this->transferPolicy->canTransferToDepartment($employee, $targetDept)) {
            throw new CannotTransferException();
        }

        // Execute transfer
        $workplace->updateWorkplace($targetDept, null);
        $this->workplaceRepo->save($workplace);

        // Infrastructure concern
        $this->eventDispatcher->dispatch(new EmployeeTransferred($employee));
    }
}
```

### This Specific Case: EmployeeAggregateService

```php
// WRONG LOCATION
namespace App\Domain\Employee\Service;

class EmployeeAggregateService
{
    public function __construct(
        private EmployeeRepositoryInterface $employeeRepo, // ← Repository injection = red flag
        // ...
    ) {
    }
}

// CORRECT LOCATION
namespace App\Application\Employee\Service;

class EmployeeAggregateService
{
    // This orchestrates repositories → Application Service
}
```

###  Reference

│ Concern	          │ Domain Service	                                 │  Application Service                                 │
│ ------------------- │ ------------------------------------------------ │----------------------------------------------------- │
│ Location	          │ Domain\Employee\Service\	                     │  Application\Employee\Service\                       │
│ Purpose	          │ Business logic	                                 │  Orchestration                                       │
│ Dependencies	      │ Entities, Value Objects, other Domain Services	 │  Repositories, Infrastructure, Domain Services       │
│ Repository Access	  │ ❌ No	                                        │  ✅ Yes                                              │
│ Infrastructure	  │ ❌ No (no DB, HTTP, Email)	                    │  ✅ Yes                                              │
│ Returns	          │ Domain objects	                                 │  DTOs, primitives                                    │
│ Example	          │ SalaryCalculator	                             │  EmployeeAggregateService                            │
<br>

### Recommended Structure

```sh
src/
├── Domain/
│   └── Employee/
│       ├── Entity/
│       ├── Repository/ (interfaces only)
│       ├── Exception/
│       └── Service/
│           ├── EmployeeSalaryCalculator.php         ← Pure business logic
│           ├── EmployeePromotionPolicy.php          ← Pure business rules
│           └── EmployeeEligibilityChecker.php       ← Domain rules
│
└── Application/
    └── Employee/
        ├── Command/
        ├── Query/
        ├── DTO/
        └── Service/
            ├── EmployeeAggregateService.php         ← Orchestrates repos
            ├── EmployeeOnboardingService.php        ← Workflow orchestration
            └── EmployeeReportGenerator.php          ← Queries + formatting
```

EmployeeAggregateService is not part of the Domain\ rather part of the Application\ because it:

- Injects repositories
- Orchestrates multiple data sources
- Returns DTOs (not domain entities)

EmployeeAggregateService is an Application Service, not a Domain Service.

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./../images/pr-banner-long.png">
</div>