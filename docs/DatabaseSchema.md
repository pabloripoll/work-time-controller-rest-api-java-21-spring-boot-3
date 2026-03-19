<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# Core Database Schema

```bash
Legend:
[PK] = Primary Key
[FK] = Foreign Key
[UC] = Unique Constraint
[IDX] = Indexed

Database Schema
│
├── users [PRIMARY]
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [IDX] role (varchar(32), NOT NULL)
│   ├── [UC][IDX] email (varchar(64), UNIQUE, NOT NULL)
│   ├── password (varchar(256), NOT NULL)
│   ├── [IDX] created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp)
│   ├── [IDX] deleted_at (timestamp)
│   └── created_by_user_id (bigint, NOT NULL)
│
├── locations
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── is_continent (boolean, NOT NULL)
│   ├── continent_id (bigint)
│   ├── is_zone (boolean, NOT NULL)
│   ├── zone_id (bigint)
│   ├── is_country (boolean, NOT NULL)
│   ├── country_id (bigint)
│   ├── is_region (boolean, NOT NULL)
│   ├── region_id (bigint)
│   ├── is_state (boolean, NOT NULL)
│   ├── state_id (bigint)
│   ├── is_district (boolean, NOT NULL)
│   ├── district_id (bigint)
│   ├── is_city (boolean, NOT NULL)
│   ├── city_id (bigint)
│   ├── is_suburb (boolean, NOT NULL)
│   ├── suburb_id (bigint)
│   ├── [IDX] slug (varchar(128))
│   ├── [IDX] name (varchar(128))
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── masters
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] user_id (bigint, NOT NULL) → users.id
│   ├── is_active (boolean, NOT NULL)
│   ├── is_banned (boolean, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── master_access_logs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] user_id (bigint, NOT NULL) → users.id
│   ├── is_terminated (boolean, NOT NULL)
│   ├── is_expired (boolean, NOT NULL)
│   ├── [IDX] expires_at (timestamp, NOT NULL)
│   ├── refresh_count (integer, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   ├── ip_address (varchar(45))
│   ├── user_agent (text)
│   ├── requests_count (integer, NOT NULL)
│   ├── payload (json)
│   └── [IDX] token (text, NOT NULL)
│
├── master_profile
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] master_id (bigint, NOT NULL) → masters.id
│   ├── nickname (varchar(64), NOT NULL)
│   ├── avatar (text)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── admins
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] user_id (bigint, NOT NULL) → users.id
│   ├── is_active (boolean, NOT NULL)
│   ├── is_banned (boolean, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── admin_access_logs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] user_id (bigint, NOT NULL) → users.id
│   ├── is_terminated (boolean, NOT NULL)
│   ├── is_expired (boolean, NOT NULL)
│   ├── [IDX] expires_at (timestamp, NOT NULL)
│   ├── refresh_count (integer, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   ├── ip_address (varchar(45))
│   ├── user_agent (text)
│   ├── requests_count (integer, NOT NULL)
│   ├── payload (json)
│   └── [IDX] token (text, NOT NULL)
│
├── admin_capability
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] admin_id (bigint, NOT NULL) → admins.id
│   ├── [IDX] employee_id (bigint) # [FK] → employees.id
│   ├── creates_admins (boolean, NOT NULL)
│   ├── creates_employees (boolean, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── admin_profile
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] admin_id (bigint, NOT NULL) → admins.id
│   ├── nickname (varchar(64), NOT NULL)
│   ├── avatar (text)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── office_departments
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [UC] name (varchar(64), UNIQUE, NOT NULL)
│   ├── description (varchar(256), NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── office_jobs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] department_id (bigint, NOT NULL) → office_departments.id
│   ├── [UC] title (varchar(64), UNIQUE, NOT NULL)
│   ├── description (varchar(256), NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── employees
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [UC] uuid (uuid, UNIQUE, NOT NULL)
│   ├── is_active (boolean, NOT NULL)
│   ├── is_banned (boolean, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── employee_access_logs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] user_id (bigint, NOT NULL) → users.id
│   ├── is_terminated (boolean, NOT NULL)
│   ├── is_expired (boolean, NOT NULL)
│   ├── [IDX] expires_at (timestamp, NOT NULL)
│   ├── refresh_count (integer, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   ├── ip_address (varchar(45))
│   ├── user_agent (text)
│   ├── requests_count (integer, NOT NULL)
│   ├── payload (json)
│   └── [IDX] token (text, NOT NULL)
│
├── employee_workplace
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] employee_id (bigint, NOT NULL) → employees.id
│   ├── [FK][IDX] department_id (bigint) → office_departments.id
│   ├── [FK][IDX] job_id (bigint) → office_jobs.id
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── employee_location
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK] employee_id (bigint, NOT NULL) → employees.id
│   ├── [FK] continent_id (bigint) → geographics.id
│   ├── [FK] zone_id (bigint) → geographics.id
│   ├── [FK] country_id (bigint) → geographics.id
│   ├── [FK] region_id (bigint) → geographics.id
│   ├── [FK] state_id (bigint) → geographics.id
│   ├── [FK] district_id (bigint) → geographics.id
│   ├── [FK] city_id (bigint) → geographics.id
│   ├── [FK] suburb_id (bigint) → geographics.id
│   ├── address (varchar(128))
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── employee_profile
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] employee_id (bigint, NOT NULL) → employees.id
│   ├── [UC] name (varchar(64), NOT NULL) [composite unique: name+surname]
│   ├── [UC] surname (varchar(64), NOT NULL) [composite unique: name+surname]
│   ├── birthdate (date)
│   ├── avatar (text)
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── employee_contacts
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] employee_id (bigint, NOT NULL) → employees.id
│   ├── postal (varchar(64))
│   ├── email (varchar(64))
│   ├── phone (varchar(64))
│   ├── mobile (varchar(64))
│   ├── created_at (timestamp, NOT NULL)
│   └── updated_at (timestamp, NOT NULL)
│
├── employment_contract_types
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [IDX] title (varchar(64))
│   ├── references (varchar(256))
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   └── deleted_at (timestamp)
│
├── employment_contracts
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] contract_type_id (bigint, NOT NULL) → employment_contract_types.id
│   ├── [FK][IDX] employee_id (bigint, NOT NULL) → employees.id
│   ├── [FK][IDX] admin_id (bigint, NOT NULL) → admins.id
│   ├── days_per_month (integer, NOT NULL)
│   ├── days_per_week (integer, NOT NULL)
│   ├── hours_per_day (integer, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   └── deleted_at (timestamp)
│
├── employment_contracts_logs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] contract_id (bigint, NOT NULL) → employment_contracts.id
│   ├── [FK][IDX] admin_id (bigint, NOT NULL) → admins.id
│   ├── [UC] action_key (varchar(128), NOT NULL)
│   └── created_at (timestamp, NOT NULL)
│
├── employment_workdays
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] contract_id (bigint, NOT NULL) → employment_contracts.id
│   ├── [FK][IDX] employee_id (bigint, NOT NULL) → employees.id
│   ├── [IDX] starts_date (timestamp)
│   ├── ends_date (timestamp)
│   ├── hours_extra (time)
│   ├── hours_made (integer, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   └── deleted_at (timestamp)
│
├── employment_workdays_logs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] workday_id (bigint, NOT NULL) → employment_workdays.id
│   ├── [FK][IDX] admin_id (bigint, NOT NULL) → admins.id
│   ├── [UC] action_key (varchar(128), NOT NULL)
│   └── created_at (timestamp, NOT NULL)
│
├── employment_workday_clockings
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] user_id (bigint, NOT NULL) → users.id
│   ├── [FK][IDX] workday_id (bigint, NOT NULL) → employment_workdays.id
│   ├── clock_in (boolean, NOT NULL)
│   ├── clock_out (boolean, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   └── deleted_at (timestamp)
│
├── employment_workday_clockings_logs
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [FK][IDX] clocking_id (bigint, NOT NULL) → employment_workday_clockings.id
│   ├── [FK][IDX] admin_id (bigint, NOT NULL) → admins.id
│   ├── [UC] action_key (varchar(128), NOT NULL)
│   └── created_at (timestamp, NOT NULL)
│
├── service_discovery_clients [PRIMARY]
│   ├── [PK] id (bigint, NOT NULL) [auto-increment]
│   ├── [UC][IDX] uuid (uuid, UNIQUE, NOT NULL)
│   ├── [IDX] token (text, NOT NULL)
│   ├── created_at (timestamp, NOT NULL)
│   ├── updated_at (timestamp, NOT NULL)
│   └── deleted_at (timestamp)
│
└── service_discovery_logs
    ├── [PK] id (bigint, NOT NULL) [auto-increment]
    ├── [FK][IDX] client_id (bigint, NOT NULL) → service_discovery_clients.id
    ├── action ((varchar(128)), NOT NULL)
    ├── payload (text, NOT NULL)
    └── created_at (timestamp, NOT NULL)
```

## Users Heriarchy in DDD

```bash
users (authentication bridge)
├── user.id
└── user.email

masters (domain root)
├── master.id
├── master.user_id → users.id
└── master_profiles (subdomain)
    ├── profile.id
    └── profile.master_id → masters.id

admins (domain root)
├── admin.id
├── admin.user_id → users.id
├── admin_profiles (subdomain)
│   ├── profile.id
│   └── profile.admin_id → admins.id
└── admin_capabilities (subdomain)
    ├── capability.id
    └── capability.admin_id → admins.id

employees (domain root)
├── employee.id
├── employee.user_id → users.id
├── employee_profiles (subdomain)
├── employee_locations (subdomain)
├── employee_contacts (subdomain)
├── employee_workplaces (subdomain)
└── employee_capabilities (subdomain)
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>