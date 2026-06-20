# Personal Expense Tracker

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven

### Frontend

* HTML5
* CSS3
* JavaScript
* Chart.js

### Database

* MySQL


---

## Setup Instructions

### 1. Clone Repository

https://github.com/Bhargavi-Sripathi/Personal-Expense-Tracker.git cd expense-tracker

### 2. Configure Database

Create a MySQL database:

CREATE DATABASE expense_tracker;

Update application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker

spring.datasource.username=your_username

spring.datasource.password=your_password

### 3. Run Backend

mvn spring-boot:run

Backend runs at:

http://localhost:8080

### 4. Run Frontend

Open index.html using Live Server.

Frontend runs at:

http://127.0.0.1:5500

---

Built as a Full Stack Java Project using Spring Boot and MySQL.
