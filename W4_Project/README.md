# 🚗 Parking Management System

A **full-stack Parking Management System** that handles real-world parking scenarios like vehicle entry, exit, slot allocation, and billing.
The project is built using **Spring Boot (Backend)** and **Angular (Frontend)**.

---

## 📌 Features

* Create and manage parking lots
* Automatic parking slot allocation
* Vehicle entry with validation
* Vehicle exit with bill generation
* First 30 minutes free parking
* Dynamic pricing based on parking occupancy
* View active parking sessions
* View complete parking history
* Clean and simple UI

---

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Data JPA
* MySQL
* Hibernate
* REST APIs

### Frontend

* Angular
* TypeScript
* HTML & CSS
* Angular Forms
* HttpClient

---

## ⚙️ Project Architecture

### Backend Modules

* **Controller** – Handles REST API requests
* **Service** – Contains business logic
* **Repository** – Database interaction using JPA
* **DTOs** – Request/response data transfer
* **Exception Handling** – Global exception handling
* **Entities** – ParkingLot, ParkingSlot, Vehicle, ParkingSession

### Frontend Modules

* Parking Lots List
* Vehicle Entry Form
* Vehicle Exit with Receipt
* Active Sessions View
* Parking History View
* Shared Parking Service for API calls

---

## 🚙 Parking Flow

1. Admin creates a parking lot with total slots and base price
2. Slots are auto-generated for the parking lot
3. Vehicle enters:

   * Parking lot validated
   * Slot availability checked
   * Vehicle assigned a slot
4. Vehicle exits:

   * Exit time captured
   * Bill calculated
   * Slot freed
5. Bill displayed below exit form

---

## 💰 Billing Logic

* First **30 minutes are free**
* Minimum billing unit is **1 hour**
* Pricing increases based on occupancy:

  * > 50% occupied → 1.25x
  * > 80% occupied → 1.5x

---

## 🗄️ Database Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/parking_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ How to Run the Project

### Backend (Spring Boot)

1. Import project into IDE (IntelliJ / Eclipse)
2. Update MySQL credentials in `application.properties`
3. Run the Spring Boot application
4. Backend runs on:
   `http://localhost:8081`

### Frontend (Angular)

1. Navigate to frontend folder
2. Install dependencies

   ```bash
   npm install
   ```
3. Run Angular app

   ```bash
   ng serve
   ```
4. Open browser at:
   `http://localhost:4200`

---

## 📡 API Endpoints

| Method | Endpoint              | Description          |
| ------ | --------------------- | -------------------- |
| GET    | /api/parking-lots     | Get all parking lots |
| POST   | /api/parking/entry    | Vehicle entry        |
| POST   | /api/parking/exit     | Vehicle exit         |
| GET    | /api/sessions/active  | Active sessions      |
| GET    | /api/sessions/history | Parking history      |

---

## 🧠 Key Learnings

* Designing REST APIs using Spring Boot
* Handling validations and exceptions
* Implementing transactional logic
* Connecting Angular frontend with Spring backend
* Real-world billing and pricing logic
* Clean UI layout and component structure

---

## 👩‍💻 Author

**Harshita Raotole**
MCA Student – VESIT, Mumbai
Skills: Java, SQL, Spring Boot, Angular, MERN Stack

---

## ✅ Future Enhancements

* User authentication
* Admin dashboard
* Printable/downloadable receipts
* Real-time slot availability
* Deployment on cloud (AWS)

---
