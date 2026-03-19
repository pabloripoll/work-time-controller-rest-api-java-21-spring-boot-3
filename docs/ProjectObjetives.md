<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# Project Objetives

The objective is to develop an API for employee signing-in management using PHP and Symfony, applying SOLID principles, CQRS, Hexagonal Architecture and DDD.

The system must allow employees to register their arrivals and departures, as well as view a record of all their clockings. Each clock-in must contain information on the date and time of entry/exit of the associated employee and any additional relevant information.
<br>

## Applied functionalities

1. **User CRUD**: Create, read, update and delete users.

2. **CRUD of Signings**: Create, read, update and delete employee signings.

3. **Login**: Create an access login that returns a token.

4. **Check-in Registration**: Implement check-in and check-out operations for employees.

5. **List of Signings**: Show a record of all the signings of an employee, ordered by date and time.

6. **Security**: Ensure that only authenticated employees can register their clockings and access their history.
<br>

## 🎯 Purpose

This repository is designed for:
- **Learning**: Understanding modern backend architecture in PHP
- **Reference**: Production-ready code examples and patterns
- **Experimentation**: Testing advanced architectural concepts
- **Portfolio**: Showcasing professional development practices

## 🏗️ Architecture & Design Patterns

The application implements industry-standard architectural patterns:

### **SOLID Principles**
- Single Responsibility
- Open/Closed
- Liskov Substitution
- Interface Segregation
- Dependency Inversion

### **Domain-Driven Design (DDD)**
- Clear domain boundaries
- Ubiquitous language
- Aggregates and entities
- Value objects
- Domain services

### **CQRS (Command Query Responsibility Segregation)**
- Separated read and write operations
- Command handlers for mutations
- Query handlers for data retrieval
- Clear intent and responsibility

### **Hexagonal Architecture (Ports & Adapters)**
- Domain layer independence
- Infrastructure abstraction
- Testable business logic
- Flexible adapter implementations

### **Event-Driven Design**
- Domain events
- Event dispatching
- Audit logging system
- Decoupled components

## ✨ Key Features

### 👥 **User Management**
- Multi-role authentication (Master, Admin, Employee)
- JWT-based security
- Role-based access control (RBAC)
- User profile management

### 📝 **Employment Contracts**
- Contract type management (Permanent, Temporary, Part-Time, Internship, Contractor)
- Contract terms and conditions
- GDPR/LOPD compliance tracking
- Contract lifecycle management

### ⏰ **Time Tracking**
- Employee clock-in/clock-out
- Workday management
- Automatic hours calculation
- Extra hours tracking

### 📊 **Audit System**
- Complete action logging
- Admin activity tracking
- Change history
- Compliance reporting

### 🌍 **Geographic Data**
- Unified geolocation structure
- Multi-level location hierarchy
- Employee location tracking

### 🏢 **Office Management**
- Department structure
- Job positions
- Workplace assignments

## Cons

Hexagonal + CQRS is intentionally verbose. Each use case is one class on purpose: it makes each operation independently testable, replaceable, and readable. The trade-off is file count.

<br>

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>