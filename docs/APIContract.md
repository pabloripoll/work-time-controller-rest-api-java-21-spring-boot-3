<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

The architectural decision made for users identification is the "Internal ID vs. External ID" (or Public/Private ID) pattern.

Using BIGINT for internal foreign keys ensures your SQL JOIN operations are incredibly fast and indexes stay small.

Exposing a UUID only when an entity needs to be referenced by external microservices (or public APIs), preventing ID guessing/enumeration (Insecure Direct Object Reference - IDOR) and hiding your database volume.

# REST API Contract

- Contracts for users with ROLE_MASTER are separate for security reasons and only allow them to create ROLE_ADMIN users.
- Contracts for users with ROLE_ADMIN are separate for security reasons and allow them to create other ROLE_ADMIN and ROLE_MEMBER users.
- Contracts for users with ROLE_EMPLOYEE are basic but do not allow them to access users with ROLE_ADMIN or ROLE_MASTER.

```bash
# Sets employee as admin (*) creates a user ROLE_ADMIN with employee related information except that email must be admin.(already employee email) and nickname coming from email without the @domain.com part

# ROLE_MASTER only
ROLE_MASTER  POST    /api/v1/master/auth/login                    #-> ROLE_MASTER login endpoint
ROLE_MASTER  GET     /api/v1/master/account/profile               #-> Reads its own profile
ROLE_MASTER  PATCH   /api/v1/master/account/settings/profile      #-> Updates its own profile except passwords
ROLE_MASTER  PATCH   /api/v1/master/account/settings/password     #-> Updates its password (old_password and retyped_new_password required)
ROLE_MASTER  GET     /api/v1/master/users/masters                 #-> Lists only ROLE_MASTER users
ROLE_MASTER  POST    /api/v1/master/users/masters                 #-> Creates user master (ROLE_MASTER)
ROLE_MASTER  PATCH   /api/v1/master/users/masters/{id}/settings/profile     #-> Updates its own profile except passwords
ROLE_MASTER  PATCH   /api/v1/master/users/masters/{id}/settings/password    #-> Updates its password (old_password and retyped_new_password required)
ROLE_MASTER  GET     /api/v1/master/users/admins                            #-> Lists only ROLE_ADMIN users
ROLE_MASTER  GET     /api/v1/master/users/employees                         #-> Lists of employed users ROLE_EMPLOYEE
ROLE_MASTER  PUT     /api/v1/master/users/employees/{id}/role-admin/apply   #-> Sets employee as admin (*)
ROLE_MASTER  PUT     /api/v1/master/users/employees/{id}/role-admin/revoke  #-> Removes employee as admin but set admin user as banned

# ROLE_ADMIN only
ROLE_ADMIN  POST    /api/v1/admin/auth/login                    #-> ROLE_ADMIN login endpoint
ROLE_ADMIN  GET     /api/v1/admin/account/profile               #-> Reads its own profile
ROLE_ADMIN  PATCH   /api/v1/admin/account/settings/profile      #-> Updates its own profile except passwords
ROLE_ADMIN  PATCH   /api/v1/admin/account/settings/password     #-> Updates its password (old_password and retyped_new_password required)

ROLE_ADMIN  GET     /api/v1/admin/users/admins                            #-> Lists only ROLE_ADMIN users
ROLE_ADMIN  POST    /api/v1/admin/users/admins                            #-> Creates user admin (ROLE_ADMIN)
ROLE_ADMIN  GET     /api/v1/admin/users/admins/{id}/profile               #-> Reads specific administrator employee profile
ROLE_ADMIN  PUT     /api/v1/admin/users/employees/{id}/role-admin/apply   #-> Sets employee as admin (*)
ROLE_ADMIN  PUT     /api/v1/admin/users/employees/{id}/role-admin/revoke  #-> Removes employee as admin but set admin user as banned

ROLE_ADMIN  GET     /api/v1/admin/users/employees                         #-> Lists of employed users ROLE_EMPLOYEE
ROLE_ADMIN  POST    /api/v1/admin/users/employees                         #-> Creates employee users ROLE_EMPLOYEE (employees/workers)
ROLE_ADMIN  DELETE  /api/v1/admin/users/employees/{id}                    #-> Removes specific employee user by id
ROLE_ADMIN  GET     /api/v1/admin/users/employees/{id}/profile            #-> Reads specific employee profile
ROLE_ADMIN  PATCH   /api/v1/admin/users/employees/{id}/settings/profile   #-> Updates employee user by id profile except password
ROLE_ADMIN  PATCH   /api/v1/admin/users/employees/{id}/settings/password  #-> Updates employee user by id password (retyped_new_password required)

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