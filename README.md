# Note Application — Full Stack (Spring Boot + React)

A secure full-stack note-taking application built using **Spring Boot** for the backend and **React (Vite)** for the frontend.

This project focuses on **backend fundamentals**, **JWT-based authentication**, and **real frontend–backend integration**, rather than UI design.  
The main goal was to understand how a production-style application works end to end — from database to browser.

---

## Live Demo

**Frontend (Vercel):**  
-> https://note-irfan.vercel.app/

**Backend (Cloud-hosted):**  
-> Deployed using Docker on cloud platforms (Render / DigitalOcean during development)

---

##  Project Overview

This application allows users to:

- Register and log in securely
- Create, read, update, and delete their own notes
- Search notes by title or content
- Access notes securely (users cannot access other users’ data)

Authentication is **stateless** and handled using **JWT (JSON Web Tokens)**.  
Every protected request must include a valid token.

---

## High-Level Architecture


-----------------------

React (Vite)
      |
      |  HTTP requests (JWT in Authorization header)
      v
   Spring Boot REST API
      |
      |  JPA / Hibernate
      v
   PostgreSQL Database


**Key ideas:**

- Frontend never talks directly to the database
- Backend does not store sessions
- Each request is authenticated using JWT

---

## Backend (Spring Boot)

### Tech Stack

- Java 17
- Spring Boot 3
- Spring Security 6
- JWT (Stateless Authentication)
- Spring Data JPA
- PostgreSQL
- Docker
- Bruno / Postman (API testing)

---

### Controllers

#### 1️. Authentication Controller

Handles:
- User registration
- User login
- JWT token generation

**Endpoints:**

POST /api/auth/register
POST /api/auth/login

#### 2\. Notes Controller

Handles **user-specific note operations**:

*   Create a note
    
*   Fetch logged-in user’s notes
    
*   Update a note
    
*   Delete a note
    
*   Search notes (case-insensitive)
    

All endpoints are **JWT protected**.

GET    /api/notes
POST   /api/notes
PUT    /api/notes/{id}
DELETE /api/notes/{id}

#### 3\. Admin Controller (Learning Purpose)

*   Created only for experimenting with role-based access
    
*   Not fully implemented
    
*   Used to learn @PreAuthorize and role handling
    

### Security Design

*   Stateless authentication using JWT
    
*   Passwords stored securely using **BCrypt**
    
*   Logged-in user extracted using @AuthenticationPrincipal
    
*   Notes are always queried with userId to prevent unauthorized access
    
*   Centralized exception handling
    

### Global Exception Handling

A centralized @RestControllerAdvice handles:

*   Invalid credentials
    
*   Unauthorized access
    
*   Resource not found
    
*   Validation errors
    
*   Generic server errors
    

All API responses follow a **consistent structure**.

Frontend (React + Vite)
-----------------------

### Tech Stack

*   React
    
*   Vite
    
*   Fetch API
    
*   LocalStorage (JWT storage)
    

### Features Implemented

*   User registration
    
*   User login
    
*   JWT token storage and usage
    
*   Notes CRUD (Create, Read, Update, Delete)
    
*   Search notes by title/content
    
*   Inline edit and delete
    
*   Logout
    

The UI is intentionally simple — focus was on **logic and correctness**, not styling.

### Frontend Structure

src/
├── api/
│   ├── auth.js
│   └── notes.js
├── pages/
│   ├── Login.jsx
│   └── Note.jsx
    └── Register.jsx
└── App.jsx

*   JWT is stored in localStorage
    
*   Every protected request sends:
    

Authorization: Bearer <token>

Frontend–Backend Integration
----------------------------

*   CORS configured explicitly for frontend domain
    
*   Health check endpoint used during early testing
    
*   Proper handling of:
    
    *   Expired tokens
        
    *   Unauthorized responses
        
    *   Network errors
        

Database
--------

*   PostgreSQL
    
*   Managed database during cloud deployment
    
*   Can be accessed using:
    
    *   pgAdmin
        
    *   DBeaver
        

**Tables:**

*   users
    
*   notes
    

Each note belongs to **exactly one user**.

Environment Variables
---------------------

Backend uses environment variables for security:

DATABASE_URL
DATABASE_USERNAME
DATABASE_PASSWORD
JWT_SECRET

Sensitive values are **never committed to GitHub**.

Testing
-------

*   Manual API testing using Bruno / Postman
    
*   Repository-level testing explored using @DataJpaTest
    
*   Focused on understanding correctness rather than heavy automation
    

Deployment
----------

### Backend

*   Dockerized Spring Boot application
    
*   Deployed as a Web Service
    
*   Connected to PostgreSQL via environment variables
    

### Frontend

*   Deployed on Vercel
    
*   Communicates with backend using public API URL
    

What I Learned
--------------

*   How JWT-based stateless authentication works
    
*   Why user identity must come from SecurityContext
    
*   How backend response structure affects frontend logic
    
*   How cloud deployment differs from local development
    
*   Why finishing and deploying a project matters more than over-planning
    

Future Improvements
-------------------

*   Refresh token support
    
*   Better UI styling (Tailwind / component library)
    
*   Pagination in frontend
    
*   Role-based admin features
    
*   Automated tests (JUnit, Mockito)
    
*   Rate limiting and audit logs
  
##  Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/irfankhansajid/note.git](https://github.com/irfankhansajid/note.git)
    cd note
    ```

2.  **Configure Database:** Create a database named `notebook_db` in PostgreSQL via DBeaver or pgAdmin. Update `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://<your-db-url>
    spring.datasource.username=<your-username>
    spring.datasource.password=<your-password>

    ```

3.  **Build and Run:**
    ```bash
    ./mvnw spring-boot:run
    ```
    

License
-------

MIT License
Distributed under the MIT License. See `LICENSE` for more information.

This project represents a **complete learning milestone** in building a real-world full-stack application, covering backend security, frontend integration, and cloud deployment


