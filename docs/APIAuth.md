<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
<br><br>

# API Auth

Stateless API with JWT

## Scripts

- ./config/packages/security.yaml

- ./src/Infrastructure/Event/Listener/JWTCreatedListener.php

- ./src/Infrastructure/Security/JwtAuthenticationEntryPoint.php

- ./src/Infrastructure/Security/CustomAuthenticationSuccessHandler.php

- ./src/Application/Employee/Service/EmployeeAuthenticationService.php

- ./src/Presentation/Http/Rest/User/UserAuthController.php

- ./src/Infrastructure/Security/ApiAccessDeniedHandler.php

- ./src/Infrastructure/Http/AuthenticatedEmployeeResolver.php *(not in used but is for further development)*
<br><br>

## CORS

- ./config/packages/nelmio_cors.yaml

Update `allow_origin` property that is set for testing only. Change to your Application IP instead.
<br><br>

## Test users

For manually testing porpuses, there are fixtured main users to use them on front-end on development stages.

Master
- master@webmaster.com
- Pass12B4?

Admin
- wortic.superadmin@example.com
- Pass123A

Employee
- wortic.admin@example.com
- Pass1234

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>