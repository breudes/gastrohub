# Gastrohub - User Management API
This project is a Spring Boot application with PostgreSQL designed for user management, including authentication and access control.
The API follows REST standards and is structured in layers for easy maintenance, scalability, and testing.

## 📌 Technologies Used

- Java 21
- Maven (dependency management)
- Spring Boot
- Spring MVC (REST API development)
- Spring Data JPA (data persistence)
- Spring Security (authentication and authorization)
- PostgreSQL

## 📂 Project Structure

```bash
src/main/java/com/breudes/gastrohub
│
├── controller        # REST Controllers
│   ├── AuthenticationController.java
│   └── UserController.java
│
├── dto               # Data Transfer Objects
├── infrastructure    # Configurations and integrations
├── model             # Database entities
├── repository        # JPA repository interfaces
├── service           # Business logic
└── GastrohubApplication.java  # Main application class
```

## ⚙️ Database Configuration
In the application.properties  file, configure your PostgreSQL credentials:

```properties
spring.datasource.url=YOUR_POSTGRES_DATASOURCE_URL
spring.datasource.username=YOUR_POSTGRES_DATASOURCE_USERNAME
spring.datasource.password=YOUR_POSTGRES_DATASOURCE_PASSWORD
api.security.secret=YOUR-JWT-SECRET
```

## 🚀 Running the Project

1. Clone the repository

```bash
git clone https://github.com/your-username/gastrohub.git
cd gastrohub
```

2. Set up PostgreSQL database

2.1 Create Database

```sql
CREATE DATABASE gastrohub;
```

2.2 Execute init.sql with the initial data (for test purpose)

```sql
psql -U your_username -d gastrohub -f src/main/resources/init.sql
```

3. Run the application

```bash
./mvnw spring-boot:run
```

## 📡 Main Endpoints

| Method | Endpoint      | Description               | Permissions          |
| ------ | ------------- | ------------------------- | -------------------- |
| POST   | `/login`      | Authenticate user         | Admin, Client, Owner |
| GET    | `/users`      | Get all users             | Admin                |
| GET    | `/users/{id}` | Get a specific user by ID | Admin                |
| PUT    | `/users/{id}` | Update a specific user by ID | Admin, Client, Owner |
| PATCH    | `/users/{id}` | Update a specific user's password by ID | Admin, Client, Owner |
| DELETE    | `/users/{id}` | Delete on a logic way a specific user by ID | Admin |

## 🛡️ Authentication & Authorization
The system uses JWT (JSON Web Token) for authentication.
When logging in, the user receives a token that must be sent in the Authorization header for each protected request:

```makefile
Authorization: Bearer <token>
```

## Author
- Brenda Alexandra de Souza Silva
- LinkedIn/Github: breudes

## 📜 License

This project is licensed under the MIT License. Feel free to use and modify it.
