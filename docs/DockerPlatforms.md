<div id="top-header" style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>

# WORKTIME CONTROLLER - JAVA SPRING BOOT 3

- [/README.md](../README.md)
- [Platforms Repository](#platforms-repository)
- [API Installation](#api-installation)
<br><br>

# Project Installation in Local Environment

## <a id="platforms-repository"></a>Platforms Repository

You can start by cloning the Docker Platforms Repository [platform-docker-nginx-php-8.5](https://github.com/pabloripoll/platform-docker-nginx-php-8.5)
```bash
$ git clone https://github.com/pabloripoll/platform-docker-nginx-php-8.5.git
```

From https://github.com/pabloripoll?tab=repositories you can clone other resources for this project, extract from its `./platform/` directory, the platform required to be required

In the root of platform repository directory, you need to set up the platforms containers variable for the best automation to build and maitain them.

Copy the .env.example file to .env, delete the comments and choose the ports available locally *(it is recommended that they be followed)*
```bash
# REMOVE COMMENTS WHEN COPY THIS FILE AND TRIM TRAILING WHITESPACES
# Ask the team for recommended values

# DOCKER VARIABLES FOR AUTOMATION
SUDO=sudo                                       # <- how local user executes system commands - leave it empty if not necessary ----------------------> #
DOCKER=sudo docker                              # <- how local user executes Docker commands --------------------------------------------------------> #
DOCKER_COMPOSE=sudo docker compose              # <- how local user executes "docker compose" or docker-compose command -----------------------------> #

# CONTAINERS BASE INFORMATION FOR BUILDING WITH docker-compose.yml
PROJECT_NAME="PLATFORMS DOCKER"                 # <- project name will be used for automation outputs -----------------------------------------------> #
PROJECT_LEAD=abbr                               # <- abbreviation or acronym name as part of the container tag that is useful relationship naming ---> #
PROJECT_HOST="127.0.0.1"                        # <- machine hostname referrer - not necessary for this project -------------------------------------> #
PROJECT_CNET=mp-dev                             # <- useful when a network is required for container connections between each other -----------------> #

# DATABASE - LOCAL
DATABASE_PLTF=pgsql-18.2                        # <- platform assets directory's name ---------------------------------------------------------------> #
DATABASE_IMGK=postgres:18.2-alpine3.23          # <- real main image keys to manage automations for sharing resources -------------------------------> #
DATABASE_PORT=7710                              # <- local machine port opened for container service ------------------------------------------------> #
DATABASE_CAAS=mp-pgsql-dev                      # <- container name to build the service - it is important to set the environment in this variable --> #
DATABASE_CAAS_CPUS=2.00                         # <- container's maximum CPUs usage to apply by docker-compose - leave it empty for full usage ------> #
DATABASE_CAAS_MEM=128M                          # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
DATABASE_CAAS_SWAP=512M                         # <- container's RAM swap space in storage executed by automation command ---------------------------> #
DATABASE_ROOT="root"                            # <- database root password -------------------------------------------------------------------------> #
DATABASE_NAME=projdb_local                      # <- database name ----------------------------------------------------------------------------------> #
DATABASE_USER=devuser                           # <- database user ----------------------------------------------------------------------------------> #
DATABASE_PASS="devpass"                         # <- database password ------------------------------------------------------------------------------> #
DATABASE_PATH="/resources/database/"            # <- sql file's directory ---------------------------------------------------------------------------> #
DATABASE_INIT=pgsql-init.sql                    # <- init sql file ----------------------------------------------------------------------------------> #
DATABASE_BACK=pgsql-backup.sql                  # <- backup sql file --------------------------------------------------------------------------------> #

# API - LOCAL
APIREST_PLTF=nginx-php-8.5                      # <- platform assets directory's name ---------------------------------------------------------------> #
APIREST_IMGK=alpine3.23-nginx-php8.5            # <- real main image keys to manage automations for sharing resources -------------------------------> #
APIREST_PORT=7711                               # <- local machine port opened for container service ------------------------------------------------> #
APIREST_BIND="../../../apirest"                 # <- path where application is binded from container to local ---------------------------------------> #
APIREST_CAAS=mp-api-dev                         # <- container name to build the service - it is important to set the environment in this variable --> #
APIREST_CAAS_USER=osuser                        # <- container's project directory user -------------------------------------------------------------> #
APIREST_CAAS_GROUP=osgroup                      # <- container's project directory group ------------------------------------------------------------> #
APIREST_CAAS_MEM=128M                           # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
APIREST_CAAS_SWAP=512M                          # <- container's RAM swap space in storage executed by automation command ---------------------------> #

# WEBAPP - LOCAL
WEBAPP_PLTF=nginx-nodejs-25                     # <- platform assets directory's name ---------------------------------------------------------------> #
WEBAPP_IMGK=alpine3.23-nginx-nodejs25           # <- real main image keys to manage automations for sharing resources -------------------------------> #
WEBAPP_PORT=7712                                # <- local machine port opened for container service port 80 ----------------------------------------> #
WEBAPP_PORT_DEV=7713                            # <- local machine port opened for container service extra port -------------------------------------> #
WEBAPP_BIND="../../../application"              # <- path where application is binded from container to local ---------------------------------------> #
WEBAPP_CAAS=mp-app-dev                          # <- container name to build the service - it is important to set the environment in this variable --> #
WEBAPP_CAAS_USER=osuser                         # <- container's project directory user -------------------------------------------------------------> #
WEBAPP_CAAS_GROUP=osgroup                       # <- container's project directory group ------------------------------------------------------------> #
WEBAPP_CAAS_CPUS=2.00                           # <- container's maximum CPUs usage to apply by docker-compose - leave it empty for full usage ------> #
WEBAPP_CAAS_MEM=128M                            # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
WEBAPP_CAAS_SWAP=512M                           # <- container's RAM swap space in storage executed by automation command ---------------------------> #

# MAILER - LOCAL
MAILER_PLTF=mailhog-1.0                         # <- platform assets directory's name ---------------------------------------------------------------> #
MAILER_IMGK=mailhog:alpine-3.12-mailhog-1.0     # <- real main image keys to manage automations for sharing resources -------------------------------> #
MAILER_PORT=7714                                # <- local machine port opened for container service ------------------------------------------------> #
MAILER_CAAS=mp-mailhog-dev                      # <- container name to build the service - it is important to set the environment in this variable --> #
MAILER_CAAS_MEM=128M                            # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
MAILER_CAAS_SWAP=512M                           # <- container's RAM swap space in storage executed by automation command ---------------------------> #
MAILER_APP_PORT=7715                            # <- application ui management port -----------------------------------------------------------------> #

# BROKER - LOCAL
BROKER_PLTF=rabbitmq-4.2                        # <- platform assets directory's name ---------------------------------------------------------------> #
BROKER_IMGK=rabbitmq:4.2-management-alpine      # <- real main image keys to manage automations for sharing resources -------------------------------> #
BROKER_PORT=7716                                # <- local machine port opened for container service ------------------------------------------------> #
BROKER_BIND="./rabbitmq_data"                   # <- platform broker data storage in local ----------------------------------------------------------> #
BROKER_CAAS=mp-rabbitmq-dev                     # <- container name to build the service - it is important to set the environment in this variable --> #
BROKER_CAAS_MEM=128M                            # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
BROKER_CAAS_SWAP=512M                           # <- container's RAM swap space in storage executed by automation command ---------------------------> #
BROKER_APP_PORT=7717                            # <- application ui management port -----------------------------------------------------------------> #
BROKER_APP_USER=guest                           # <- application ui management user -----------------------------------------------------------------> #
BROKER_APP_PASS=guest                           # <- application ui management password -------------------------------------------------------------> #
BROKER_APP_COOKIE="guest"                       # <- application security ---------------------------------------------------------------------------> #
BROKER_APP_NODENAME=rabbit@rabbitmq             # <- application configuration ----------------------------------------------------------------------> #

# MONGODB - LOCAL
MONGODB_PLTF=mongodb-8.2                        # <- platform assets directory's name ---------------------------------------------------------------> #
MONGODB_IMGK=mongo:8.2.4                        # <- real main image keys to manage automations for sharing resources -------------------------------> #
MONGODB_BIND="../data"                          # <- container persistant data on local machine -----------------------------------------------------> #
MONGODB_CAAS=mp-mongodb-dev                     # <- container name to build the service - it is important to set the environment in this variable --> #
MONGODB_CAAS_MEM=512M                           # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
MONGODB_CAAS_SWAP=1G                            # <- container's RAM swap space in storage executed by automation command ---------------------------> #
MONGODB_PORT=7718                               # <- local machine port opened for container service ------------------------------------------------> #
MONGODB_NAME=projddb_local                      # <- default database name --------------------------------------------------------------------------> #
MONGODB_USER=rootuser                           # <- database root user -----------------------------------------------------------------------------> #
MONGODB_PASS=rootpass                           # <- database root password -------------------------------------------------------------------------> #
MONGODB_APP_PORT=7719                           # <- local machine port opened for container service client -----------------------------------------> #
MONGODB_APP_USER=devuser                        # <- database user ----------------------------------------------------------------------------------> #
MONGODB_APP_PASS=devpass                        # <- database password ------------------------------------------------------------------------------> #

# REDIS - LOCAL
REDIS_PLTF=redis-8.6                            # <- platform assets directory's name ---------------------------------------------------------------> #
REDIS_IMGK=alpine-3.23-redis-8.6                # <- real main image keys to manage automations for sharing resources -------------------------------> #
REDIS_PORT=7720                                 # <- local machine port opened for container service ------------------------------------------------> #
REDIS_BIND="../data"                            # <- container persistant data on local machine -----------------------------------------------------> #
REDIS_CAAS=mp-redis-dev                         # <- container name to build the service - it is important to set the environment in this variable --> #
REDIS_CAAS_MEM=128M                             # <- container's maximum RAM usage to apply by docker-compose ---------------------------------------> #
REDIS_CAAS_SWAP=512M                            # <- container's RAM swap space in storage executed by automation command ---------------------------> #
REDIS_ROOT_USER=rootuser                        # <- database root user -----------------------------------------------------------------------------> #
REDIS_ROOT_PASS=rootpass                        # <- database root password -------------------------------------------------------------------------> #
REDIS_APP_USER=devuser                          # <- database user ----------------------------------------------------------------------------------> #
REDIS_APP_PASS=devpass                          # <- database password ------------------------------------------------------------------------------> #
```

### Checkout platforms commands automation

This repository contains a Makefile to automate commands. You can see the commands it contains as follows
```bash
$ make help
Usage: $ make [target]
Targets:
$ make help                           shows this Makefile help message

$ make local-info                     shows local machine ip and container ports set
$ make local-ownership                shows local ownership
$ make local-ownership-set            sets recursively local root directory ownership

# Shorthand for all services
$ make services-set                   sets all container services
$ make services-create                builds and starts up all container services
$ make services-info                  shows all container services information
$ make services-destroy               destroys all container services

# REST API - Symfony
$ make apirest-hostcheck              shows this project ports availability on local machine for apirest container
$ make apirest-info                   shows the apirest docker related information
$ make apirest-set                    sets the apirest enviroment file to build the container
$ make apirest-create                 creates the apirest container from Docker image
$ make apirest-network                creates the apirest container network - execute this recipe first before others
$ make apirest-ssh                    enters the apirest container shell
$ make apirest-start                  starts the apirest container running
$ make apirest-stop                   stops the apirest container but its assets will not be destroyed
$ make apirest-restart                restarts the running apirest container
$ make apirest-destroy                destroys completly the apirest container

# Mailhog
$ make mailer-hostcheck               shows this project ports availability on local machine for mailer container
$ make mailer-info                    shows the mailer docker related information
$ make mailer-set                     sets the mailer enviroment file to build the container
$ make mailer-create                  creates the mailer container from Docker image
$ make mailer-network                 creates the mailer container network - execute this recipe first before others
$ make mailer-ssh                     enters the mailer container shell
$ make mailer-start                   starts the mailer container running
$ make mailer-stop                    stops the mailer container but its assets will not be destroyed
$ make mailer-restart                 restarts the running mailer container
$ make mailer-destroy                 destroys completly the mailer container

# Postgre
$ make db-hostcheck                   shows this project ports availability on local machine for database container
$ make db-info                        shows docker related information
$ make db-set                         sets the database enviroment file to build the container
$ make db-create                      creates the database container from Docker image
$ make db-network                     creates the database container external network
$ make db-ssh                         enters the apirest container shell
$ make db-start                       starts the database container running
$ make db-stop                        stops the database container but its assets will not be destroyed
$ make db-restart                     restarts the running database container
$ make db-destroy                     destroys completly the database container with its data
$ make db-test-up                     creates a side database for testing porpuses
$ make db-test-down                   drops the side testing database
$ make db-sql-install                 migrates sql file with schema / data into the container main database to init a project
$ make db-sql-replace                 replaces the container main database with the latest database .sql backup file
$ make db-sql-backup                  copies the container main database as backup into a .sql file
$ make db-sql-drop                    drops the container main database but recreates the database without schema as a reset action

# MongoDB
$ make mongodb-hostcheck              shows this project ports availability on local machine for database container
$ make mongodb-info                   shows docker related information
$ make mongodb-set                    sets the database enviroment file to build the container
$ make mongodb-create                 creates the database container from Docker image
$ make mongodb-network                creates the database container external network
$ make mongodb-ssh                    enters the apirest container shell
$ make mongodb-start                  starts the database container running
$ make mongodb-stop                   stops the database container but its assets will not be destroyed
$ make mongodb-restart                restarts the running database container
$ make mongodb-destroy                destroys completly the database container with its data

# Redis
$ make redis-hostcheck                shows this project ports availability on local machine for database container
$ make redis-info                     shows docker related information
$ make redis-set                      sets the database enviroment file to build the container
$ make redis-create                   creates the database container from Docker image
$ make redis-network                  creates the database container external network
$ make redis-ssh                      enters the apirest container shell
$ make redis-start                    starts the database container running
$ make redis-stop                     stops the database container but its assets will not be destroyed
$ make redis-restart                  restarts the running database container
$ make redis-destroy                  destroys completly the database container with its data

# RabbitMQ
$ make broker-hostcheck               shows this project ports availability on local machine for broker container
$ make broker-info                    shows the broker docker related information
$ make broker-set                     sets the broker enviroment file to build the container
$ make broker-create                  creates the broker container from Docker image
$ make broker-network                 creates the broker container network - execute this recipe first before others
$ make broker-ssh                     enters the broker container shell
$ make broker-start                   starts the broker container running
$ make broker-stop                    stops the broker container but its assets will not be destroyed
$ make broker-restart                 restarts the running broker container
$ make broker-destroy                 destroys completly the broker container
$ make repo-flush                     echoes clearing commands for git repository cache on local IDE and sub-repository tracking remove
$ make repo-commit                    echoes common git commands
```
<br>

Before building up the platforms containers, proceed to clone the REST API repository.

## <a id="api-installation"></a>API Installation

- Delete the contents of the `./apirest` directory from both local and GIT cache:
```bash
$ git rm -r --cached -- "apirest/*" ":(exclude)apirest/.gitkeep"
$ git clean -fd
$ git reset --hard
$ git commit -m "Remove apirest directory and its default installation"
```

- Clone inside the `./apirest` directory [work-time-controller-restapi-symfony-7](https://github.com/pabloripoll/work-time-controller-restapi-symfony-7)
```bash
$ cd ./apirest
$ git clone https://github.com/pabloripoll/work-time-controller-restapi-symfony-7 .
```

- The `./apirest` directory is now a **standalone repository** and will not be tracked as a submodule in the main repository. You can use `git` commands freely within `apirest` from locally or from within the container.

Once the platform is installed and the REST API container is running, you must execute the initialization commands
```bash
$ make apirest-ssh
```

<!-- FOOTER -->
<br>

---

<br>

- [GO TOP ⮙](#top-header)

<div style="with:100%;height:auto;text-align:right;">
    <img src="./images/pr-banner-long.png">
</div>