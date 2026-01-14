# 📊 Investment Banking Deal Pipeline Management System

A **full-stack, production-style web application** designed to manage investment banking deals across multiple stages, users, and roles.
Built with **Angular**, **Spring Boot**, **MongoDB**, and deployed on **Netlify + Render** with **JWT-based authentication**.

---

## 🔗 Live Demo

### Frontend (Netlify)

👉 **[https://comfy-stroopwafel-55b5a7.netlify.app](https://comfy-stroopwafel-55b5a7.netlify.app)**

### Backend (Render)

👉 **[https://investment-banking-deal-pipeline.onrender.com](https://investment-banking-deal-pipeline.onrender.com)**

> ⚠️ **Note:** The backend is hosted on Render’s free tier.
> The first request may take **30–60 seconds** due to cold start. Subsequent requests are fast.

---

## 🏗️ System Architecture

```
Angular SPA (Netlify)
        |
        | HTTPS (REST APIs)
        ↓
Spring Boot Backend (Render)
        |
        ↓
MongoDB Atlas (Cloud Database)
```

* Frontend served via Netlify CDN
* Backend secured with Spring Security + JWT
* MongoDB Atlas used for persistent storage
* CORS configured for production deployment

---

## ✨ Key Features

### 🔐 Authentication & Security

* JWT-based authentication
* Role-based access control (Admin / User)
* Secure password hashing using BCrypt
* Stateless session management

### 📈 Deal Management

* Create, view, edit, and delete deals
* Deal pipeline visualization by stages
* Deal notes and value tracking
* Sector and deal-type classification

### 👥 User Management (Admin)

* Create and manage users
* Activate / deactivate users
* Assign roles

### 📊 Dashboard

* Aggregated deal statistics
* Pipeline summary view
* Quick insights for decision-making

---

## 🛠️ Tech Stack

### Frontend

* **Angular (Standalone Components)**
* TypeScript
* SCSS
* Angular Router
* HTTP Interceptors
* Netlify (Hosting)

### Backend

* **Spring Boot**
* Spring Security
* JWT (JSON Web Tokens)
* Spring Data MongoDB
* Maven
* Render (Hosting)

### Database

* **MongoDB Atlas**

---

## 📁 Project Structure

```
investment-banking-deal-pipeline/
├── deal-pipeline-ui-frontend/   # Angular frontend
│   ├── src/
│   ├── public/_redirects        # Netlify SPA routing
│   ├── angular.json
│   └── package.json
│
├── deal-pipeline-backend/       # Spring Boot backend
│   ├── src/main/java
│   ├── src/main/resources
│   └── pom.xml
│
├── docker-compose.yml
└── README.md
```

---

## 🌐 SPA Routing (Important)

This project is a **Single Page Application (SPA)**.
Netlify routing is configured using `_redirects`:

```
/*    /index.html   200
```

This ensures routes like `/login` and `/dashboard` work correctly on refresh and mobile devices.

---

## 🔑 Environment Configuration

### Backend (Render)

The following environment variables are configured on Render:

* `SPRING_DATA_MONGODB_URI`
* `SPRING_DATA_MONGODB_DATABASE`
* `JWT_SECRET`

### Frontend

API base URL is configured in:

```ts
environment.prod.ts
```

```ts
apiUrl: 'https://investment-banking-deal-pipeline.onrender.com/api';
```

---

## 🚀 Deployment Details

### Frontend

* Hosted on **Netlify**
* Automatic deployment on every push to `main`
* Global CDN for fast delivery

### Backend

* Hosted on **Render**
* Dockerized Spring Boot application
* Auto-deploy on GitHub push
* Cold-start behavior on free tier

---

## 🧪 Local Development (Optional)

### Frontend

```bash
cd deal-pipeline-ui-frontend
npm install
npm start
```

### Backend

```bash
cd deal-pipeline-backend
./mvnw spring-boot:run
```

---

## 🔒 Security Notes

* Only authentication endpoints are publicly accessible
* All business APIs require a valid JWT
* CORS restricted to Netlify production domain
* Passwords are never stored in plain text

---

## 📌 Future Enhancements

* Refresh token support
* Audit logs
* Advanced role permissions
* Pagination & filtering improvements
* CI/CD optimization
* Performance monitoring

---

## 👤 Author

**Vaishnavi Subash**
Full-Stack Java Developer
(Spring Boot • Angular • MongoDB)

---

## ⭐ Final Note

This project demonstrates:

* Real-world full-stack architecture
* Secure authentication & authorization
* Cloud deployment challenges and solutions
* Production-grade SPA routing & CORS handling

It is intended as a **portfolio project** showcasing practical backend and frontend engineering skills.

