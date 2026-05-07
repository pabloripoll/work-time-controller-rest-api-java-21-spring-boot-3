<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER

- [/README.md](../README.md)
<br><br>

# API Auth

Stateless API with JWT

## Test users

For manually testing porpuses, the API seeder provides default users to use them on front-end on development stages.

Master
- master@webmaster.com
- Pass12B4?

Super Admin
- wortic.superadmin@example.com
- Pass123A

Admin
- wortic.admin@example.com
- Pass1234B

Employee
- employee@example.com
- Pass1234
<br>

## Scripts involved into Auth process

- ./src/main/java/api/dev/infrastructure/security/jwt/JwtProperties.java
- ./src/main/java/api/dev/infrastructure/persistence/user/UserJpaEntity.java
- ./src/main/java/api/dev/infrastructure/config/SecurityConfig.java
- ./src/main/java/api/dev/infrastructure/security/filter/JsonLoginFilter.java
- ./src/main/java/api/dev/infrastructure/security/handler/AuthSuccessHandler.java
- ./src/main/java/api/dev/infrastructure/security/handler/AuthFailureHandler.java
- ./src/main/java/api/dev/infrastructure/security/filter/JwtAuthenticationFilter.java
- ./src/main/java/api/dev/infrastructure/security/handler/JwtAuthenticationEntryPoint.java
- ./src/main/java/api/dev/infrastructure/config/SecurityBeansConfig.java
- ./src/main/java/api/dev/presentation/exception/GlobalExceptionHandler.java
- ./src/main/java/api/dev/presentation/rest/domain/master/MasterAuthController.java

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>