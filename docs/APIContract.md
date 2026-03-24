<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# REST API Contract Design

API design is about organizing how information is received and returned so that different clients — such as web applications, mobile apps, or other services — can access data in a clear, consistent, and reliable way. The goal is to make it easy to request the right information, process it through the application, and return the correct result, whether the data is stored in a database or in files.

A **REST**ful web **API** implementation is a web API that employs Representational State Transfer (REST) architectural principles to achieve a stateless, loosely coupled interface between a client and service. A web API that is RESTful supports the standard HTTP protocol to perform operations on resources and return representations of resources that contain hypermedia links and HTTP operation status codes.

A RESTful web API should align with the following principles:

- **Platform independence**, which means that clients can call the web API regardless of the internal implementation. To achieve platform independence, the web API uses HTTP as a standard protocol, provides clear documentation, and supports a familiar data exchange format such as JSON or XML.

- **Loose coupling**, which means that the client and the web service can evolve independently. The client doesn't need to know the internal implementation of the web service, and the web service doesn't need to know the internal implementation of the client. To achieve loose coupling in a RESTful web API, use only standard protocols and implement a mechanism that allows the client and the web service to agree on the format of the data to exchange.

Continue reading the following article:
- https://learn.microsoft.com/en-us/azure/architecture/best-practices/api-design
<br><br>

## Authentication Strategy

The architectural decision made for users identification is the "Internal ID vs. External ID" (or Public/Private ID) pattern. So, to use this API as backend service the authentication process is as any other application but for microservices, through user UUID *(Universally Unique Identifier)* which is crucial for cloud computing.

Using BIGINT for internal foreign keys ensures SQL JOIN operations be incredibly fast and indexes stay small.

Exposing a UUID only when an entity needs to be referenced by external microservices (or public APIs), preventing ID guessing/enumeration (Insecure Direct Object Reference - IDOR) and hides database volume.

## User Roles

- User with ROLE_MASTER are intended system support and they are only allowed to create ROLE_ADMIN users if it is required.
- ROLE_ADMIN user are the main feature of this API. The manage an watch all users reports. They can create others ROLE_ADMIN users and they are the only ones that can create ROLE_MEMBER users.
- ROLE_EMPLOYEE actions are the basic. User registration is restricted to be done by ROLE_ADMIN users and they are not allowed to access any other users information.

## REST API Contract

```bash
# Sets employee as admin (*) creates a user ROLE_ADMIN with employee related information except that email must be admin.(already employee email) and nickname coming from email without the @domain.com part

# ROLE_MASTER only
ROLE_MASTER  POST    /api/v1/master/auth/login                    #-> ROLE_MASTER login endpoint
ROLE_MASTER  GET     /api/v1/master/account/profile               #-> Reads its own profile
ROLE_MASTER  PATCH   /api/v1/master/account/settings/profile      #-> Updates its own profile except passwords
ROLE_MASTER  PATCH   /api/v1/master/account/settings/password     #-> Updates its password (old_password and retyped_new_password required)
ROLE_MASTER  POST    /api/v1/master/account/settings/avatar       #-> Uploads avatar image and set url
ROLE_MASTER  DELETE  /api/v1/master/account/settings/avatar       #-> Deletes avatar image and remove url
ROLE_MASTER  GET     /api/v1/master/users/masters                 #-> Lists only ROLE_MASTER users
ROLE_MASTER  POST    /api/v1/master/users/masters                 #-> Creates user master (ROLE_MASTER)
ROLE_MASTER  DELETE  /api/v1/master/users/masters/{master_id}                       #-> Deletes user master (cannot delete itself)
ROLE_MASTER  PATCH   /api/v1/master/users/masters/{master_id}/settings/profile      #-> Updates another master profile except if it is supermaster user
ROLE_MASTER  PATCH   /api/v1/master/users/masters/{master_id}/settings/password     #-> Updates another master password except if it is supermaster user
ROLE_MASTER  PUT     /api/v1/master/users/masters/{master_id}/supermaster/apply     #-> Assigns user role master as supermaster
ROLE_MASTER  PUT     /api/v1/master/users/masters/{master_id}/supermaster/revoke    #-> Revokes supermaster feature to user role master
ROLE_MASTER  GET     /api/v1/master/users/admins                                    #-> Lists only ROLE_ADMIN users
ROLE_MASTER  GET     /api/v1/master/users/employees                                 #-> Lists of employed users ROLE_EMPLOYEE
ROLE_MASTER  PUT     /api/v1/master/users/employees/{master_id}/role-admin/apply    #-> Sets employee as admin (*)
ROLE_MASTER  PUT     /api/v1/master/users/employees/{master_id}/role-admin/revoke   #-> Removes employee as admin but set admin user as banned

# ROLE_ADMIN only
ROLE_ADMIN  POST    /api/v1/admin/auth/login                    #-> ROLE_ADMIN login endpoint
ROLE_ADMIN  GET     /api/v1/admin/account/profile               #-> Reads its own profile
ROLE_ADMIN  PATCH   /api/v1/admin/account/settings/profile      #-> Updates its own profile except passwords
ROLE_ADMIN  PATCH   /api/v1/admin/account/settings/password     #-> Updates its password (old_password and retyped_new_password required)
ROLE_ADMIN  POST    /api/v1/admin/account/settings/avatar       #-> Uploads avatar image and set url
ROLE_ADMIN  DELETE  /api/v1/admin/account/settings/avatar       #-> Deletes avatar image and remove url

ROLE_ADMIN  GET     /api/v1/admin/users/admins                      #-> Lists only ROLE_ADMIN users
ROLE_ADMIN  POST    /api/v1/admin/users/admins                      #-> Creates user admin (ROLE_ADMIN)
ROLE_ADMIN  GET     /api/v1/admin/users/admins/{admin_id}/profile   #-> Reads specific administrator employee profile
ROLE_ADMIN  PUT     /api/v1/admin/users/admins/{admin_id}/superadmin/apply    #->
ROLE_ADMIN  PUT     /api/v1/admin/users/admins/{admin_id}/superadmin/revoke   #->

ROLE_ADMIN  GET     /api/v1/admin/users/employees                         #-> Lists of employed users ROLE_EMPLOYEE
ROLE_ADMIN  POST    /api/v1/admin/users/employees                         #-> Creates employee users ROLE_EMPLOYEE (employees/workers)
ROLE_ADMIN  DELETE  /api/v1/admin/users/employees/{employee_id}                    #-> Removes specific employee user by id
ROLE_ADMIN  GET     /api/v1/admin/users/employees/{employee_id}/profile            #-> Reads specific employee profile
ROLE_ADMIN  PATCH   /api/v1/admin/users/employees/{employee_id}/settings/profile   #-> Updates employee user by id profile except password
ROLE_ADMIN  PATCH   /api/v1/admin/users/employees/{employee_id}/settings/password  #-> Updates employee user by id password (retyped_new_password required)
ROLE_ADMIN  POST    /api/v1/admin/users/employees/{employee_id}/settings/avatar    #-> Uploads avatar image and set url
ROLE_ADMIN  DELETE  /api/v1/admin/users/employees/{employee_id}/settings/avatar    #-> Deletes avatar image and remove url
ROLE_ADMIN  PUT     /api/v1/admin/users/employees/{employee_id}/role-admin/apply   #-> Sets employee as admin (*)
ROLE_ADMIN  PUT     /api/v1/admin/users/employees/{employee_id}/role-admin/revoke  #-> Removes employee as admin but set admin user as banned

ROLE_ADMIN  GET     /api/v1/admin/employees/contracts                   #-> List of employee contracts by date
ROLE_ADMIN  GET     /api/v1/admin/employees/contracts/filters           #-> List of contract types
ROLE_ADMIN  GET     /api/v1/admin/employees/contracts/{employee_id}     #-> Reads the employee's contract
ROLE_ADMIN  PATCH   /api/v1/admin/employees/contracts/{employee_id}     #-> Updates an employee's workday clock-in/out
ROLE_ADMIN  DELETE  /api/v1/admin/employees/contracts/{employee_id}     #-> Deletes an employee's workday clock-in/out

ROLE_ADMIN  GET     /api/v1/admin/employees/workdays/{employee_id}             #-> Lists employee workdays ordered by date
ROLE_ADMIN  POST    /api/v1/admin/employees/workdays/{employee_id}             #-> Creates a date or batch of dates workdays
ROLE_ADMIN  GET     /api/v1/admin/employees/workdays/{employee_id}/{date}      #-> Reads a workdate data (clock-in/out records)
ROLE_ADMIN  DELETE  /api/v1/admin/employees/workdays/{employee_id}/{date}      #-> Deletes a user's workdate record
ROLE_ADMIN  POST    /api/v1/admin/employees/workdays/{employee_id}/{date}/clock-in   #-> Creates employee's workday clock-in
ROLE_ADMIN  POST    /api/v1/admin/employees/workdays/{employee_id}/{date}/clock-out  #-> Creates employee's workday clock-out
ROLE_ADMIN  PATCH   /api/v1/admin/employees/workdays/{employee_id}/{date}/clockings/{id} #-> Updates an employee's workday clock-in/out
ROLE_ADMIN  DELETE  /api/v1/admin/employees/workdays/{employee_id}/{date}/clockings/{id} #-> Deletes an employee's workday clock-in/out

# ROLE_EMPLOYEE - All employees are limited to request its own data and update only its password
ROLE_EMPLOYEE POST    /api/v1/auth/login                    #-> Member role login
ROLE_EMPLOYEE GET     /api/v1/account/profile               #-> Member read profile only by itself
ROLE_EMPLOYEE PATCH   /api/v1/account/settings/password     #-> Update password (old_password and retyped_new_password required)
ROLE_EMPLOYEE GET     /api/v1/account/contract/{uid}        #-> Reads the employee's contract
ROLE_EMPLOYEE GET     /api/v1/account/contract/{uid}/workdays            #-> Lists employee workdays ordered by date
ROLE_EMPLOYEE POST    /api/v1/account/contract/{uid}/workdays            #-> Creates a date or batch of dates for the employee workdays
ROLE_EMPLOYEE GET     /api/v1/account/contract/{uid}/workdays/{date}     #-> Reads a workdate data, e.g.: clock-in/out records
ROLE_EMPLOYEE DELETE  /api/v1/account/contract/{uid}/workdays/{date}     #-> Deleting a specific user's workdate record and its dependecies
ROLE_EMPLOYEE POST    /api/v1/account/contract/{uid}/workdays/{date}/clock-in        #-> Creates an employee workday clock-in
ROLE_EMPLOYEE POST    /api/v1/account/contract/{uid}/workdays/{date}/clock-out       #-> Creates an employee workday clock-out
ROLE_EMPLOYEE PATCH   /api/v1/account/contract/{uid}/workdays/{date}/clockings/{id}  #-> Updates an employee workday clock-in/out record by id
ROLE_EMPLOYEE DELETE  /api/v1/account/contract/{uid}/workdays/{date}/clockings/{id}  #-> Deletes an employee workday clock-in/out record by id

# ANY_ROLE
ANY_ROLE    POST    /api/v1/auth/refresh        #-> Token refresh (Stateless - JWT)
ANY_ROLE    POST    /api/v1/auth/logout         #-> Token deletion (Stateless - JWT)
ANY_ROLE    GET     /api/v1/auth/whoami         #-> Token user data (Stateless - JWT)

# NO AUTHENTICATION IS REQUIRED
PUBLIC      GET     /api/v1/test                #-> API version 1 connection test
PUBLIC      GET     /api/v1/test/database       #-> Core Postgre database connection test
PUBLIC      GET     /api/v1/test/mailer         #-> Mailer connection test
PUBLIC      GET     /api/v1/test/broker         #-> RabbitMQ connection test
PUBLIC      GET     /api/v1/test-redis          #-> Redis connection test
PUBLIC      GET     /api/v1/test/event-db       #-> MongoDB connection test
PUBLIC      GET     /api/v1/test/all            #-> whole connection tests
```

You can consult the routes created for the project with the following command
```bash
$ php bin/console debug:router --show-controllers | grep "/api/"
```

## Service

```bash
# Request employee clocking status
curl --request GET \
  --url 'https://worktic.pabloripoll.com/api/v1/connections/[client_uuid]/employee' \
  --header 'authorization: Bearer {connection_token}' \
  --header 'content-type: application/json' \
  --data '{"options":{"clock_status":{"employee_uuid":"{employee_uuid}","datetime":"{timestamp}"}}}'
# response {"data":{"employee_fullname":"John Doe","last_clocking_action":"clock_in","datetime":"{timestamp}"}}

curl --request POST \
  --url 'https://worktic.pabloripoll.com/api/v1/connections/[client_uuid]/employee' \
  --header 'authorization: Bearer {connection_token}' \
  --header 'content-type: application/json' \
  --data '{"options":{"clock_in":{"employee_uuid":"{employee_uuid}","datetime":"{timestamp}"}}}'
# response {"data":{"employee_fullname":"John Doe","current_clocking_action":"clock_in","datetime":"{timestamp}"}}
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>