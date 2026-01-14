Perfect — you already have the **right skeleton**.
I’ll now give you a **complete, professional, interview-ready README.md** that you can **copy–paste as-is**.

This README is written the way **real backend / full-stack engineers** write it, and it explains *what*, *why*, and *how* clearly to recruiters.

---

# 📊 Investment Banking Deal Pipeline Management Portal

A **full-stack enterprise-style web application** that simulates how **investment banks manage and track deal pipelines** such as **Merger, Acquisition, IPOs, debt, and equity financing**.
The system demonstrates **role-based access control, secure authentication, workflow management, and containerized deployment**.

This project is built to reflect **real-world backend and system design practices**, not just UI functionality.

---

## 🚀 Tech Stack

### Frontend

* **Angular 18**
* Angular Material
* TypeScript
* Nginx (production static hosting)

### Backend

* **Spring Boot 3**
* Spring Security
* JWT Authentication
* Spring Data MongoDB
* Swagger / OpenAPI

### Database

* **MongoDB**

### DevOps & Deployment

* Docker
* Docker Compose
* Multi-stage Docker builds
* Nginx reverse proxy

---

## ✨ Key Features

### 🔐 Authentication & Security

* JWT-based authentication
* Secure login and token handling
* Stateless REST APIs
* Password encryption using BCrypt

### 👥 Role-Based Access Control

* **ADMIN**

  * Manage users
  * View all deals
  * Full dashboard access
* **USER**

  * View assigned deals only
  * Limited dashboard visibility

### 📈 Deal Pipeline Management

* Create, update, and track deals
* Deal stages:

  * Prospect
  * Under Evaluation
  * Term Sheet Submitted
  * Closed / Lost
* Sector, type, and value classification

### 📝 Collaboration

* Add notes to deals
* Track deal history and updates

### 🛠 Admin Management

* Create and manage users
* Activate / deactivate accounts
* Assign roles

### 📄 API Documentation

* Interactive **Swagger UI**
* JWT-secured API testing
* OpenAPI 3 specification

---

## 🧠 System Architecture

```
Browser
  ↓
Angular SPA (Nginx)
  ↓ /api
Spring Boot REST API
  ↓
MongoDB
```

### Architecture Highlights

* SPA frontend served via Nginx
* Backend exposed through REST APIs
* JWT-secured endpoints
* Database isolated from application containers
* Docker networking for inter-service communication

---

## 🐳 Run Locally with Docker (Recommended)

### Prerequisites

* Docker
* Docker Compose
* Java 17+
* Node.js 20+ (for local development only)

---

### 1️⃣ Build Backend JAR

```bash
cd backend/deal-pipeline
mvn clean package -DskipTests
```

---

### 2️⃣ Run Entire Stack

From project root:

```bash
docker compose down -v
docker compose build --no-cache
docker compose up
```

---

### 3️⃣ Access the Application

| Service     | URL                                                                            |
| ----------- | ------------------------------------------------------------------------------ |
| Frontend    | [http://localhost:4200](http://localhost:4200)                                 |
| Backend API | [http://localhost:8080](http://localhost:8080)                                 |
| Swagger UI  | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| MongoDB     | localhost:27017                                                                |

---

## 🔑 Default Demo Credentials

The application auto-seeds users on first startup using a **CommandLineRunner** (development/demo only).

| Role  | Username | Password |
| ----- | -------- | -------- |
| ADMIN | admin    | admin123 |
| USER  | user     | user123  |

> ⚠️ In production, auto-seeding would be disabled or environment-controlled.

---

## 📘 API Documentation (Swagger)

Swagger UI is enabled for development:

```
http://localhost:8080/swagger-ui.html
```

Features:

* JWT authentication support
* Global `Authorize` button
* Interactive endpoint testing
* OpenAPI 3 compliant documentation

---

## 🔐 Security Design Notes

* Stateless JWT authentication
* Passwords stored using BCrypt hashing
* Role-based authorization enforced at API level
* Sensitive fields protected based on role
* No database credentials stored in code

---

## 🧪 Development Notes

* MongoDB runs as a **separate container**, simulating real cloud databases
* Containers are ephemeral; data persistence is handled via Docker volumes
* Frontend and backend are independently deployable
* Nginx is used for SPA routing and reverse proxying `/api` calls

---

## 🌍 Production Deployment Strategy

In production, the application is deployed as:

* **Frontend** → Netlify (Angular static build)
* **Backend** → Cloud platform (Render / Railway / Fly.io)
* **Database** → MongoDB Atlas

This mirrors real-world SaaS deployments.

---

## 📌 Project Motivation

This project was built to:

* Demonstrate **backend engineering fundamentals**
* Apply **real authentication & authorization**
* Practice **Dockerized system design**
* Build a **resume-grade full-stack application**

It is intentionally designed to go beyond CRUD and reflect **enterprise workflows**.

---

## 📄 License

This project is for educational and portfolio purposes.

---

## 👤 Author

**Vaish**
Aspiring Java Backend / Full-Stack Developer
Focused on Spring Boot, REST APIs, and system design

---

### ✅ Next Steps (Optional Enhancements)

* Audit logging
* Pagination & filtering
* Email notifications
* Flyway / migration scripts
* CI/CD pipeline


