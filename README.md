# Kids Party Manager – Frontend

## Project Overview

This project is built using Vaadin (server-side UI framework) integrated with Spring Boot. 

It provides a simple and intuitive user interface for managing children's party reservations with related resources and presents data in a clear and structured way.

The application communicates with the backend system via REST API
(available here: [Kids Party Manager](https://github.com/MartaGlad/Kids-Party-Manager-backend))

It allows users to:
- view key business metrics (e.g., reservation statuses, animator ratings),
- manage reservations, including browsing and filtering by status and date,
- create new reservations using a form dialog,
- perform CRUD operations on animators, orderers, and event packages,
- preview pricing dynamically.

---

## Features

### Dashboard View
Displays key statistics:
- reservations by status (NEW, CONFIRMED, COMPLETED, CANCELLED),
- total number of animators,
- number of active animators,
- event packages,
- orderers.

### Animators View
- full CRUD operations,
- average rating per animator,
- optimized data fetching (ratings loaded once and mapped).

### Orderers View
- full CRUD operations for managing customers.

### Event Packages View
- managing available packages,
- creating new packages with pricing and constraints.

### Reservations View
- list of reservations,
- status highlighting with colors,
- filtering by status and date,
- creating new bookings.

### Pricing Preview
- selecting package, number of children and date,
- calculating price dynamically,
- view results in multiple currencies.

---

## Architecture

The application is structured with separation of responsibilities:

- **View** → Vaadin UI components 
- **Service** → communication layer with backend 
- **Client** → REST calls 
- **DTO** → data transfer objects matching backend responses 
- **Config** → application configuration 

Additional details:
- error handling is implemented using user notifications,
- grid components are optimized to minimize unnecessary API calls.

---

## Technologies

- Java 21 
- Spring Boot 
- Vaadin
- Spring Validation (Jakarta Validation) 
- Lombok 
- Gradle (build tool)

---

## Running the Application

### 1. Make sure backend is running

This frontend depends on the backend API.

Default backend URL:

```http://localhost:8080/api/v1```


### 2. Clone the repository
Open a terminal and run:

```bash
git clone https://github.com/MartaGlad/Kids-Party-Manager-frontend.git
cd Kids-Party-Manager-frontend
```

### 3. Build the project

```bash
./gradlew build
```

### 4. Run the application
```bash
./gradlew bootRun
```

### 5. Open in browser

```http://localhost:8081```
