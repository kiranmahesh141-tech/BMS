Book Management System:

Project Overview: 

This is a RESTful web application built using Spring Boot to manage books in a bookstore system. It aims to demonstrate the development of CRUD (Create, Read, Update, Delete) APIs with robust validation, role-based security, and extensive testing.

The application features:

REST endpoints to perform CRUD operations on book records

Logical validation including ISBN format checking

Role-based access control using Spring Security

Global exception handling for cleaner error management

Unit and integration tests using JUnit 5 and MockMvc

Features
CRUD Endpoints: Manage books with endpoints supporting GET, POST, PUT, DELETE methods.

Book Entity Validation: Includes field constraints and a custom ISBN validation utility.

Security: Two roles with method-level security:

Admin: Full access to create, update, delete, and read.

User: Read-only access.

Database: Uses H2 in-memory database with Spring Data JPA.

Exception Handling: Custom exceptions and global handler with meaningful responses.

Testing: High coverage of unit and integration tests ensuring API and logic correctness.

Book Entity Specification
Field	Type	Constraints
id	Long	Auto-generated
title	String	Required, length 1–100 characters
author	String	Required, length 1–50 characters
publishedDate	LocalDate	Required
genre	String	Optional
price	BigDecimal	Required, positive value
isbn	String	Required, valid ISBN-10 or ISBN-13
API Endpoints
Endpoint	HTTP Method	Roles	Description
/books	GET	User, Admin	Get list of all books
/books/{id}	GET	User, Admin	Get book details by ID
/books	POST	Admin	Create a new book
/books/{id}	PUT	Admin	Update existing book
/books/{id}	DELETE	Admin	Delete book by ID
Security Configuration
Users:

Admin: admin / admin123

User: user / user123

Implemented using Spring Security in-memory authentication.

Method-level access control with @PreAuthorize annotations.

Validation
Bean validation for entity fields (JSR-303 standard).

Custom ISBN validator supporting:

10-digit numeric string (e.g., 1234567890)

13-digit numeric string (e.g., 9781234567890)

Tests validate correct and incorrect ISBNs.

Exception Handling
Global exception handling using @ControllerAdvice.

Handles:

BookNotFoundException

InvalidBookException

Validation errors (method arguments and constraints).

Testing
JUnit 5 unit tests covering core logic (e.g., ISBN validation).

Integration tests with Spring MockMvc for API endpoints.

Test cases include:

Book creation with valid and invalid data

Access controls for roles

Error handling scenarios (e.g., not found, unauthorized access).

Getting Started
Prerequisites
Java 17 or higher

Gradle build tool
Using Gradle:

bash
./gradlew build
./gradlew bootRun
Application runs at http://localhost:8080.

Testing API with Postman
Steps to test APIs via Postman:

Create a new collection (e.g., "Book Management API").

Add requests with Basic Auth for admin or user credentials.

Example requests:

Get all books:
Method: GET
URL: http://localhost:8080/books
Auth: user or admin

Get book by ID:
Method: GET
URL: http://localhost:8080/books/{id}
Auth: user or admin

Create book (admin only):
Method: POST
URL: http://localhost:8080/books
Auth: admin
Body (JSON):

json
{
  "title": "Spring Boot Guide",
  "author": "Author Name",
  "publishedDate": "2023-01-01",
  "genre": "Technology",
  "price": 29.99,
  "isbn": "1234567890"
}
Update book (admin only):
Method: PUT
URL: http://localhost:8080/books/{id}
Auth: admin
Body: JSON with updated data.

Delete book (admin only):
Method: DELETE
URL: http://localhost:8080/books/{id}
Auth: admin

Optional Enhancements
Pagination support on list endpoint (/books?page=&size=).

Swagger/OpenAPI documentation available at /swagger-ui.html.

Sorting books by published date or price.

Project Structure
text
src/
 ├── main/
 │    └── java/com/example/bookstore/
 │         ├── controller/
 │         ├── service/
 │         ├── model/
 │         ├── repository/
 │         ├── exception/
 │         ├── security/
 │         └── util/
 ├── test/
      └── java/com/example/bookstore/
           ├── controller/
           ├── service/
           └── validator/

This README offers a clear guide for understanding, running, and testing the Book Management Spring Boot application effectively. Let me know if additional instructions or customization are needed.
