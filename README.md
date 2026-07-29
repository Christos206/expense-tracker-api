Expense Tracker API
Overview

Expense Tracker API is a backend REST API built with Spring Boot that allows users to manage their personal expenses.

The application provides user authentication using JWT and ensures that each user can only access and manage their own expenses.

The API documentation is available through Swagger UI.

After running the application, access:

http://localhost:8080/swagger-ui/index.html
Running the Application
Requirements
Java installed
PostgreSQL installed
Maven installed (or use the included Maven wrapper)
Setup
Clone the repository:
git clone https://github.com/your-username/expense-tracker-api.git
Configure your database settings in:
application.properties
Run the application:
./mvnw spring-boot:run

The API will start on:

http://localhost:8080
