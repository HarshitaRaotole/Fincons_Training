

# 🚗 Parking Management System

A **full-stack Parking Management System** that handles real-world parking scenarios like vehicle entry, exit, slot allocation, billing, and real-time updates.

The project is built using:

* **Spring Boot (Backend)**
* **Angular with Angular Material (Frontend)**
* **Apache Kafka for real-time messaging**
* **Pagination for efficient data handling**
* **Unit Testing and Integration Testing**

---

# 📌 Features

### Core Features

* Create and manage parking lots
* Automatic parking slot allocation
* Vehicle entry with validation
* Vehicle exit with bill generation
* First 30 minutes free parking
* Dynamic pricing based on parking occupancy
* View active parking sessions
* View complete parking history

### Advanced Features

* Pagination for active sessions and parking history
* Angular Material UI for modern user interface
* Real-time updates using Apache Kafka and WebSocket
* Unit Testing using JUnit and Mockito
* Integration Testing using Testcontainers and PostgreSQL
* REST API-based architecture
* Clean and modular project structure

---

# 🛠️ Tech Stack

## Backend

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL / PostgreSQL (Testcontainers)
* Apache Kafka
* JUnit
* Mockito
* Testcontainers
* Maven

## Frontend

* Angular
* TypeScript
* Angular Material
* HTML & CSS
* Angular Forms
* HttpClient
* WebSocket

---

# ⚙️ Project Architecture

## Backend Modules

* Controller – Handles REST API requests
* Service – Contains business logic
* Repository – Database interaction using JPA
* DTOs – Request/response data transfer
* Kafka Producer – Sends parking events
* Kafka Consumer – Consumes parking events
* WebSocket Config – Enables real-time updates
* Exception Handling – Global exception handling
* Entities –

  * ParkingLot
  * ParkingSlot
  * Vehicle
  * ParkingSession

---

## Frontend Modules

* Parking Lots Component
* Vehicle Entry Component
* Vehicle Exit Component
* Active Sessions Component (with Pagination)
* Parking History Component (with Pagination)
* Receipt Dialog (Angular Material)
* Ticket Dialog (Angular Material)
* WebSocket Service for real-time updates
* Shared Parking Service for API calls

---

# 🚙 Parking Flow

1. Admin creates a parking lot with total slots and base price
2. Slots are auto-generated
3. Vehicle enters:

   * Parking lot validated
   * Slot availability checked
   * Slot assigned
   * Kafka event published
4. Vehicle exits:

   * Exit time captured
   * Bill calculated
   * Slot freed
   * Kafka event published
5. Frontend receives real-time update via WebSocket

---

# 💰 Billing Logic

* First 30 minutes are free
* Minimum billing unit is 1 hour

Dynamic pricing:

* > 50% occupied → 1.25x
* > 80% occupied → 1.5x

---

# 📄 Pagination Support

Pagination is implemented for:

* Active Parking Sessions
* Parking History

Backend uses:

* Spring Data Pageable

Frontend uses:

* Angular Material Paginator

Benefits:

* Improved performance
* Efficient data loading
* Better user experience

---

# 📡 Kafka Real-Time Flow

Backend:

* Kafka Producer sends events on:

  * Vehicle entry
  * Vehicle exit

Kafka Consumer:

* Consumes events
* Sends updates via WebSocket

Frontend:

* Receives real-time updates
* Updates UI automatically

---

# 🧪 Testing

## Unit Testing

Implemented using:

* JUnit
* Mockito

Tested:

* ParkingService logic
* Billing logic
* Slot allocation

---

## Integration Testing

Implemented using:

* Testcontainers
* PostgreSQL container

Tested:

* Database integration
* Repository layer
* Full service flow

---

# 🗄️ Database Configuration

application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/parking_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# ▶️ How to Run the Project

## Backend (Spring Boot)

1. Open backend project in IntelliJ or Eclipse

2. Start Kafka and Zookeeper using Docker:

```bash
docker-compose up -d
```

3. Run Spring Boot application

Runs on:

```
http://localhost:8081
```

---

## Frontend (Angular)

Navigate to frontend folder:

```bash
cd parking-ui
```

Install dependencies:

```bash
npm install
```

Run Angular app:

```bash
ng serve
```

Open:

```
http://localhost:4200
```

---

# 📡 API Endpoints

| Method | Endpoint              | Description                 |
| ------ | --------------------- | --------------------------- |
| GET    | /api/parking-lots     | Get all parking lots        |
| POST   | /api/parking/entry    | Vehicle entry               |
| POST   | /api/parking/exit     | Vehicle exit                |
| GET    | /api/sessions/active  | Active sessions (Paginated) |
| GET    | /api/sessions/history | Parking history (Paginated) |

---

# 🧠 Key Learnings

* Spring Boot REST API development
* Angular frontend development
* Apache Kafka integration
* Real-time updates using WebSocket
* Pagination using Spring Pageable and Angular Material
* Unit Testing using JUnit and Mockito
* Integration Testing using Testcontainers
* Docker and containerized testing
* Full-stack application architecture

---

# 👩‍💻 Author

Harshita Raotole

Skills:

* Java
* Spring Boot
* Angular
* SQL
* MERN Stack
* Apache Kafka

---

# 🚀 Future Enhancements

* User authentication and authorization
* Admin dashboard
* Role-based access
* Printable receipts
* Cloud deployment (AWS / Docker)
* Monitoring using Kafka metrics

---


