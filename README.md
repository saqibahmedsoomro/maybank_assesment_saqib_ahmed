# Transaction Management System - Maybank Assessment

This project is a Spring Boot application designed for batch processing and management of transaction data. It features file-based ingestion via Spring Batch, RESTful APIs for transaction operations, and Swagger for API documentation.

## Technologies Used

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Batch
- MySQL
- Lombok
- Swagger/OpenAPI
- JUnit 5 + Mockito (for testing)
- Gradle (build tool)

## Features

- **Batch Processing:** On application startup, reads transactions from a text file (`dataSource.txt`) and persists them to MySQL.
- **REST API Endpoints:**
  - `GET /transactions`: Fetch paginated and searchable transaction data.
  - `PUT /transactions/{id}`: Update transaction description using `description` as a request param.
- **Search Filters:** Filter by `description`, `accountNumber`, or `customerId`.
- **Swagger UI:** View and test APIs interactively via `/swagger-ui/index.html`.
- **Unit Testing:** JUnit 5 + Mockito-based tests for service and controller layers.

## Getting Started

### Prerequisites

- Java 17
- MySQL (with a schema named `maybank`)
- Gradle
- IDE (e.g., IntelliJ IDEA, Eclipse)

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/maybank-assessment.git
   cd maybank-assessment


2. Update application.properties with your MySQL credentials:
   ```code
   spring.datasource.url=jdbc:mysql://localhost:3306/maybank
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.batch.initialize-schema=always

3. Place your batch file in:
     ```css
     src/main/resources/dataSource.txt

4. Build and run the application:
    ```css
    ./gradlew bootRun

6. Access Swagger:
    ```css
    http://localhost:8080/swagger-ui/index.html

## Testing

1. Run it using:
   ```bash
   ./gradlew test

### Folder Structure
1. Folder Structure
   ```bash
   ├── config               # Spring Batch config
   ├── controller           # REST Controllers
   ├── dto                  # Data Transfer Objects
   ├── entity               # JPA Entities
   ├── repository           # JPA Repositories
   ├── service              # Business logic
   ├── test                 # Unit tests
   └── resources
       └── dataSource.txt   # Sample transaction data


### Author
Saqib Ahmed

### License
This project is for educational and assessment purposes only.
