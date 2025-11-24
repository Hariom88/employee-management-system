<<<<<<< HEAD
# employee-management-system
=======
# Employee Management System (EMS)

## Prerequisites
- Java 17 or 21
- Maven
- MySQL Server

## Configuration
The database configuration is located in `src/main/resources/application.properties`.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ems_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

**Important:**
- If your MySQL `root` user has a password, update `spring.datasource.password` in `application.properties`.
- The application will attempt to create the database `ems_db` automatically if it doesn't exist.

## Running the Application
1. Open a terminal in the project root.
2. Run the following command:
   ```sh
   mvn spring-boot:run
   ```

## API Documentation
Once the application is running, you can access the Swagger UI at:
http://localhost:8080/swagger-ui.html
>>>>>>> f347db9 (Initial commit)
