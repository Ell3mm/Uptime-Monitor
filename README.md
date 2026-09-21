# Uptime Monitor

A polyglot uptime monitoring platform built to practice production-oriented software development.

## Current stack

- Java 25 and Spring Boot 4
- PostgreSQL
- Maven Wrapper

## Run locally

Start PostgreSQL:

```powershell
docker compose up -d postgres
```

Run the API:

```powershell
./mvnw spring-boot:run
```

The first milestone is a Spring Boot API for creating and managing monitors. Python health-check workers and the TypeScript dashboard will be added in later milestones.