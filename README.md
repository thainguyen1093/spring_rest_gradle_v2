# spring_rest_gradle_v2

## Start Date: Dec 17 2020

### Architect

- restful: this project work as a service so that the client application can call to this project to get the data

### backend

- Spring:
    - boot
    - security
    - data jpa

### Database

- mysql

#### Set up project

* database: using the data.sql in this project to set up the database for this project
    - schema name: hulahooh
    - username: change the username in application.yml corresponding with local username
    - password:  change the password in application.yml corresponding with local password

#### Project Structure

- core: contain core controller and core service with default implementation for the project
- src: main source