#  Note API - Solid v1

A robust, secure RESTful backend service for personal note management. Built with **Spring Boot 3+**, **Spring Security**, and **PostgreSQL**, this API provides a stateless authentication system and optimized data handling.


##  Key Features

* **Stateless Authentication:** Secure user registration and login using **JWT (JSON Web Tokens)**.
* **Secure Password Storage:** Industry-standard password hashing using **BCrypt**.
* **Advanced Search:** Case-insensitive search across note titles and content using custom JPA queries.
* **Professional Pagination:** Server-side pagination and sorting using Spring Data `Pageable` to ensure high performance.
* **JPA Auditing:** Automatic tracking of `createdAt` and `updatedAt` timestamps for every entry.
* **Global Exception Handling:** Centralized error management for consistent API responses (e.g., 404 Not Found, 401 Unauthorized, 409 Conflict).

##  Tech Stack

* **Framework:** Spring Boot 3.4.1
* **Security:** Spring Security 6 (JWT)S
* **Database:** PostgreSQL 
* **Persistence:** Spring Data JPA 
* **API Testing:** Bruno / Postman



##  API Documentation

### 1. Authentication
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Register a new user | No |
| `POST` | `/api/auth/login` | Authenticate & get JWT | No |

### 2. Note Management
| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/notes/search` | Search notes (Paginated) | **Yes** |
| `POST` | `/api/notes` | Create a new note | **Yes** |
| `GET` | `/api/notes/{id}` | Get note by ID | **Yes** |

**Sample Search URL:** `GET http://localhost:8080/api/notes/search?searchKeyword=grocery&page=0&size=5`
**Sample Search URL:** `GET http://localhost:8080/api/notes/search?searchKeyword=grocery`

---

##  Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/irfankhansajid/note.git](https://github.com/irfankhansajid/note.git)
    cd note
    ```

2.  **Configure Database:** Create a database named `notebook_db` in PostgreSQL via DBeaver or pgAdmin. Update `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/notebook_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3.  **Build and Run:**
    ```bash
    ./mvnw spring-boot:run
    ```

##  Testing with Bruno/Postman

1.  **Register/Login:** Hit the `/api/auth/login` endpoint with your credentials to receive your JWT.
2.  **Authorize:** Copy the token and add it to your subsequent requests as a **Bearer Token**.
    * **Header Key:** `Authorization`
    * **Value:** `Bearer <your_token_here>`
3.  **Verify:** Access protected routes like `/api/notes/search`.



## License
Distributed under the MIT License. See `LICENSE` for more information.

---
*This project represents a "Solid v1" backend, transitioning from development-grade H2 to production-grade PostgreSQL with full security.*
**This project is part of a learning path towards building a Notion-like ecosystem**