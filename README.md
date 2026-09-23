# Uptime Monitor

A small polyglot uptime monitoring platform. Add websites in the dashboard,
check their current health through a Spring Boot API, and let a Python worker
record the results.

## Current stack

- Java 25 and Spring Boot 4
- PostgreSQL
- Maven Wrapper
- Python 3 standard library worker
- TypeScript dashboard

## Architecture

```text
TypeScript dashboard -> Spring Boot API -> PostgreSQL
							  ^
							  |
					Python health-check worker
```

The Java API owns monitor configuration and the latest check result. The Python
worker reads enabled monitors, makes HTTP requests, and posts each result back
to the API. The dashboard displays the monitor inventory and latest status.

## Run locally

Start PostgreSQL (requires Docker Desktop):

```powershell
docker compose up -d postgres
```

Run the API in a second terminal:

```powershell
./mvnw.cmd spring-boot:run
```

Run the Python worker in a third terminal:

```powershell
python worker/checker.py
```

Build the TypeScript dashboard (requires Node.js and npm):

```powershell
cd dashboard
npm install
npm run build
python -m http.server 3000
```

Open `http://localhost:3000` in a browser. The API runs on `http://localhost:8080`.

## API endpoints

| Method | Path | Purpose |
| --- | --- | --- |
| GET | `/api/monitors` | List monitors |
| POST | `/api/monitors` | Create a monitor |
| GET | `/api/monitors/{id}` | Get one monitor |
| PUT | `/api/monitors/{id}` | Update a monitor |
| DELETE | `/api/monitors/{id}` | Delete a monitor |
| POST | `/api/monitors/{id}/checks` | Record a worker health check |

## Validation

Run the Java tests:

```powershell
./mvnw.cmd test
```

Check Python syntax:

```powershell
python -m py_compile worker/checker.py
```

The dashboard can be compiled with `npm run build` after installing Node.js.
