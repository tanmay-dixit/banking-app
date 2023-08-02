# Banking App

Simple banking app built with Spring Boot and Postgres.

## Prerequisites

You need to have Docker installed on your machine:

## Steps to run app

1. Clone repository:
    ```
    git clone https://github.com/tanmay-dixit/banking-app.git
    cd banking-app
    ```

2. Build Docker image:
    ```
    docker build -t my-banking-app .
    ```

3. Run Docker container:

    ```
    docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker my-banking-app
    ```

   **ASSUMPTION:** This command assumes that you have a PostgreSQL instance running on your local machine
   at `localhost:5432` with a database named `postgres`, username `postgres`, and password `root`.

   If you do not have a local PostgreSQL instance, you can run one using Docker with the following command:

    ```
    docker run --name banking-app-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=postgres -p 5432:5432 -d postgres:latest
    ```

   Then run the application with the following command:

   For Windows:

    ```
    docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker my-banking-app
    ```

   For Mac:

    ```
    docker run -p 8080:8080 -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/postgres my-banking-app
    ```

The application should now be running and accessible at http://localhost:8080.

## API Documentation

You can access the API documentation at http://localhost:8080/swagger-ui.html when the application is running.
