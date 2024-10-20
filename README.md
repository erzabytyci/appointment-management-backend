# appointment-management-backend

This repository contains the backend of the Appointment Management System built with Spring Boot. The backend provides RESTful APIs for managing users, appointments, and authentication using JWT tokens.

Features
User Registration and Login with JWT Authentication
Role-based access control (Admin, Doctor, User)
CRUD operations for Users and Appointments
View available slots for appointments by date
View and manage appointment history
Secure backend with Spring Security and CORS support

Technologies Used
Java 21
Spring Boot 3.3.x
Spring Security (JWT-based authentication)
JPA / Hibernate for ORM
PostgreSQL as the database
Maven for dependency management

Getting Started

1. Prerequisites
Java 21 installed
PostgreSQL installed and running
Maven installed
Postman for testing API endpoints (optional but recommended)

3. Clone the Repository
git clone https://github.com/erzabytyci/appointment-management-backend.git
cd appointment-management-backend

4. Configure PostgreSQL Database
Create a PostgreSQL database named appointments. Update the src/main/resources/application.properties with your PostgreSQL credentials:
spring.datasource.url=jdbc:postgresql://localhost:5432/appointments
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update

5. Build the Project
Run the following Maven command to build the project:
mvn clean install

6. Run the Application
mvn spring-boot:run

The backend will be available at http://localhost:8080.
You can now use Postman to test the APIs.


API Documentation

Use Postman to test the following endpoints by sending HTTP requests.
Authentication & Authorization

POST /api/auth/login – Login with email and password (JWT Token)
Request Body: json
{
  "email": "user@example.com",
  "password": "password123"
}  ---On success, the response will include a JWT token. Add this token to the Authorization Header: Authorization: Bearer <your_jwt_token>

POST /api/auth/register – Register a new user
Request Body: json
{
  "name": "John Doe",
  "email": "johndoe@example.com",
  "password": "password123",
  "role": "USER"
}


-Admin Endpoints (Role: Admin)-

GET /api/admin/users – List all users
DELETE /api/admin/users/{id} – Delete a user
GET /api/admin/appointments – List all appointments
DELETE /api/admin/appointments/{id} – Delete an appointment

-Appointments-

GET /api/appointments – List all appointments
POST /api/appointments – Create a new appointment
Request Body:
json
{
  "appointmentDate": "2024-10-21T14:00:00",
  "branchOfMedicine": "Neurology",
  "user": { "id": 1 }
}
DELETE /api/appointments/delete/{id} – Delete an appointment
GET /api/appointments/available?date={YYYY-MM-DD} – Get available slots for a date
GET /api/appointments/history/{userId} – Get user's appointment history
GET /api/appointments/upcoming - Retrieves a list of upcoming appointments that are scheduled to occur within the next 24 hours

-Security and Roles-
JWT Authentication: Login provides a token to authorize subsequent requests.
Roles Supported:
Admin: Manage users and appointments.
Doctor: View branch-specific appointments.
User: Book, view, and delete personal appointments.
Add the JWT token to the Authorization header for secured endpoints:
Authorization: Bearer <your_jwt_token>

CORS Configuration
CORS is configured to allow requests from http://localhost:3000 (the frontend):
config.setAllowedOrigins(List.of("http://localhost:3000"));

Project Structure
appointment-management-backend/
│
├── src/main/java/com/example/appointment_management
│   ├── controller/          # Controllers (API Endpoints)
│   ├── model/               # Entity Models
│   ├── repository/          # JPA Repositories
│   ├── security/            # Security Configuration and JWT Filters
│   ├── service/             # Service Layer
│
├── src/main/resources
│   └── application.properties  # App configuration
├── pom.xml                  # Maven dependencies
└── README.md
