# 📝 **NotesApp**

## 📌 **Project Overview**

**NotesApp** is a robust backend application built using **Spring Boot** that provides a secure and scalable system for managing notes.

Beyond basic CRUD operations, the application demonstrates real-world backend capabilities including:

* 🔐 **JWT-based authentication**
* ⚡ **Redis-powered OTP verification**
* 🌦️ **Weather API integration with data storage**
* 📄 **Swagger API documentation**

The project follows a clean layered architecture and is designed for scalability, security, and maintainability.

---

## 🚀 **Features**

* ✍️ Create, read, update, and delete notes (CRUD)
* 🔐 Secure authentication using **JWT (JSON Web Tokens)**
* 📲 OTP verification system powered by **Redis**
* 🌦️ Weather API integration with persistence
* 🔗 RESTful API design
* 🍃 MongoDB integration for flexible storage
* ⚙️ Environment-based configuration (dev/prod)
* 📘 API documentation using **Swagger (OpenAPI)**
* 🧾 Centralized logging with Logback
* 🐳 Docker support

---

## 🏗️ **Tech Stack**

* **Java**
* **Spring Boot**
* **Spring Security (JWT)**
* **Spring Data MongoDB**
* **Redis**
* **Swagger / OpenAPI**
* **Maven**
* **Docker**

---

## 📁 **Project Structure**

```
NotesApp/
│── src/
│   ├── main/
│   │   ├── java/com/example/notesApp/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── security/
│   │   │   └── model/
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       ├── application-prod.yml
│   │       └── logback.xml
│   └── test/
│── Dockerfile
│── pom.xml
│── mvnw
```

---

## ⚙️ **Setup & Installation**

### ✅ **Prerequisites**

* Java 17+
* Maven
* MongoDB
* Redis
* Docker (optional)

---

### 🔧 **Run Locally**

```bash
git clone <repo-url>
cd NotesApp
```

Configure the following in `application.yml`:

* MongoDB connection
* Redis settings
* JWT secret & expiry
* Weather API key

Run the application:

```bash
./mvnw spring-boot:run
```

---

### 🐳 **Run with Docker**

```bash
docker build -t notes-app .
docker run -p 8080:8080 notes-app
```

---

## 🔐 **Authentication Flow**

1. User requests OTP
2. OTP is stored and validated using **Redis**
3. On successful verification, a **JWT token** is issued
4. Protected endpoints require JWT in request headers

---

## 📡 **API Endpoints**

| Method | Endpoint    | Description       |
| ------ | ----------- | ----------------- |
| GET    | /notes      | Get all notes     |
| GET    | /notes/{id} | Get note by ID    |
| POST   | /notes      | Create a new note |
| PUT    | /notes/{id} | Update a note     |
| DELETE | /notes/{id} | Delete a note     |

---

## 🌦️ **Weather Integration**

The application integrates with an external weather API to:

* Fetch weather data
* Store relevant data in the database
* Enable reuse and optimization of stored weather information

---

## 📚 **API Documentation**

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/
```

---

## 🌍 **Configuration Profiles**

* `application.yml` → Default configuration
* `application-dev.yml` → Development
* `application-prod.yml` → Production

Activate a profile:

```bash
-Dspring.profiles.active=dev
```

---

## 🧪 **Testing**

```bash
./mvnw test
```

---

## 📦 **Build & Run JAR**

```bash
./mvnw clean package
```

Run the generated JAR:

```bash
java -jar target/NotesApp-0.0.1-SNAPSHOT.jar
```

---

## 📝 **Logging**

Logging is configured via `logback.xml` with support for environment-specific log levels and formatting.

---

