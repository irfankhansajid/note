# 📓 Note-Taking Application

A robust, RESTful backend service for managing personal notes, built with **Spring Boot 3+** and **Spring Data JPA**. This project features advanced search capabilities and server-side pagination to handle large volumes of data efficiently.

---

## 🚀 Key Features

* **Full CRUD Operations**: Create, Read, Update, and Delete notes.
* **Advanced Search**: Integrated search functionality across titles and content with **Case-Insensitive** matching.
* **Server-side Pagination**: Optimized data fetching using Spring Data `Pageable` to ensure high performance even with thousands of notes.
* **Audit Metadata**: Automatic tracking of `createdAt` and `updatedAt` timestamps for every note.

---

## 🛠️ Tech Stack

* **Framework**: Spring Boot 3.x
* **Language**: Java 17+
* **Database**: H2 (Development) / MySQL or PostgreSQL (Production)
* **Persistence**: Spring Data JPA / Hibernate
* **Testing**: Postman/Bruno

---

## 📋 API Documentation

### 1. Search Notes (Paginated)
Retrieve a list of notes matching a specific keyword.

**Endpoint:** `GET /api/search`

| Parameter | Type | Required | Description | Default |
|:----------| :--- | :--- | :--- | :--- |
| `search`  | String | **Yes** | Keyword to search in title/content | N/A |
| `page`    | Integer | No | Page number (starts at 0) | `0` |
| `size`    | Integer | No | Number of items per page | `10` |

**Sample URL:**
`http://localhost:8080/api/search?search=grocery&page=0&size=5`

---

## ⚙️ Installation & Setup

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/irfankhansajid/note.git](https://github.com/irfankhansajid/note.git)
    cd note
    ```

2.  **Configure Database:**
    Update `src/main/resources/application.properties` with your database credentials. For development, H2 is used by default.

3.  **Build and Run:**
    ```bash
    ./mvnw spring-boot:run
    ```

---

## 🧪 Testing with Postman

1.  Set the request type to **GET**.
2.  Enter the URL: `http://localhost:8080/api/search`.
3.  In the **Params** tab, add `search` as a key and your search term as the value.
4.  The response will include a `"content"` array containing your notes and pagination metadata like `totalPages` and `totalElements`.



---

## 🤝 Contributing

1.  Fork the Project.
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`).
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`).
4.  Push to the Branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

---

## 📄 License

Distributed under the MIT License.

# Project Status

This project is complete and represents a learning-focused backend service demonstrating layered architecture, validation, exception handling, searching, and pagination.