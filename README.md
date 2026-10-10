# Personal Finance Tracker - JWT

## Requirements
JDK 17+, Maven, MySQL 8+, VS Code.

## 1. MySQL
Run:
```sql
CREATE DATABASE finance_tracker;
```

## 2. Password
Open `src/main/resources/application.properties` and replace:
`spring.datasource.password=YOUR_MYSQL_PASSWORD`
with your actual MySQL root password.

## 3. Run
In the project folder:
```bash
mvn clean
mvn spring-boot:run
```
Open http://localhost:8080/

## 4. Flow
Register -> Login -> JWT token -> Dashboard -> Add income/expense -> MySQL.

## Important
Do not open `/api/auth/login` in the browser address bar. That sends GET. The login page sends POST automatically.
