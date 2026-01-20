# ⚙️ Quote Journal - Spring Boot API

A robust RESTful backend service built with Spring Boot and Spring Security. This API serves as the core engine for the Quote Journal application, handling authentication, data persistence, and administrative controls.

---

## 🚀 Live API Endpoint
**Base URL:** [https://quote-journal-backend.onrender.com/api](https://quote-journal-backend.onrender.com/api)  
*(Note: Service may sleep on free tier; allow 30-60 seconds for initial cold start.)*

---

## 🛠️ Tech Stack & Architecture
* **Java 17 / Spring Boot 3** - Core framework.
* **Spring Security & JWT** - Secure authentication and stateless session management.
* **Spring Data JPA** - Object-Relational Mapping (ORM).
* **H2 Database** - In-memory persistence (configured for rapid development/testing).
* **Maven** - Dependency management and build automation.

---

## 🔒 Security Implementation (Banking Standards)
For this project, I implemented several enterprise-level security features:
* **Stateless Authentication:** Utilizes JSON Web Tokens (JWT) to manage user sessions securely without server-side state.
* **Role-Based Access Control (RBAC):** Distinct permissions for `USER` and `ADMIN` roles, protecting sensitive endpoints like quote deletion and user management.
* **CORS Filter:** Custom configuration to permit cross-origin requests specifically from trusted frontend domains (Vercel).
* **Password Hashing:** Uses `BCrypt` for secure credential storage.

---

## 📊 Database Schema & Seeding
The application utilizes an automated initialization strategy:
* **Auto-DDL:** Hibernate automatically manages table creation from Entity classes.
* **Data Seeding:** A `data.sql` script is used to ensure the system is pre-configured with a default Admin user and public categories upon startup.

---

## 📡 API Endpoints (Samples)

| Method | Endpoint | Access | Description |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/quotes/public` | Public | Fetches all public quotes. |
| `POST` | `/api/auth/login` | Public | Authenticates user and returns JWT. |
| `POST` | `/api/quotes` | User/Admin | Creates a new quote entry. |
| `DELETE` | `/api/quotes/{id}` | Admin | Removes a specific quote. |

---

## ⚙️ Local Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/shrutzzz01/quote-journal-backend.git](https://github.com/shrutzzz01/quote-journal-backend.git)
   cd quote-journal-backend
2. **Run the application: Using Maven:**

  ```bash

./mvnw spring-boot:run
The API will be available at http://localhost:8080/api
