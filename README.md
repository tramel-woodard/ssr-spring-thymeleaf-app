# SSR Spring Thymeleaf App
SSR application using Spring Boot and Thymeleaf

# About Application

A server-side rendered (SSR) web application built with **Spring Boot** and **Thymeleaf**. This project serves HTML views generated on the server and uses **H2 in-memory database** for development and testing purposes.

---

## Technologies Used

- **Java 17+**
- **Spring Boot 3.2.x**
  - Spring Web
  - Thymeleaf
  - Spring Boot DevTools
  - H2 Database (in-memory)
- **Maven** for dependency management
- **Thymeleaf** for HTML templating
- **H2 Console** for easy DB inspection (http://localhost:8080/h2-console)

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven (included via `./mvnw` for Unix/Mac or `mvnw.cmd` for Windows)
- IDE like IntelliJ, VS Code, or Eclipse (optional)

### How to Build

```bash
./mvnw clean install
```

### How to Run

```bash
./mvnw spring-boot:run
```

**Then open your browser and visit:**

- **Home Page**: [http://localhost:8080](http://localhost:8080)  
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

**H2 Console Settings**

- **JDBC URL**: `jdbc:h2:mem:testdb`  
- **Username**: `sa`  
- **Password**: *(leave blank)*

### Notes

- Thymeleaf templates are rendered server-side. No client-side JavaScript framework is used.
- The H2 database is reset each time the application is restarted, using an in-memory configuration.

### License

This project is provided with an MIT licence for educational and non-commercial use.