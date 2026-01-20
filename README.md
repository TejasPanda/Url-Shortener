
# 🔗 URL Shortener API

A simple and powerful URL shortener service built with Spring Boot.

---

## 📌 API Endpoints

### 1️ Create Short URL

**Request**

POST /shorten

```json
{
  "longUrl": "https://example.com",
  "customAlias": "example",
  "expiryMinutes": 10
}
```

**Response**

```json
{
  "shortUrl": "http://localhost:8080/example"
}
```

---

### 2️⃣ Redirect to Original URL

**Request**

GET /{shortCode}

**Responses**

- **302 FOUND** – if short code is valid
- **410 GONE** – if URL is expired
- **404 NOT FOUND** – if URL does not exist

---

### 3️⃣ Get URL Statistics

**Request**

GET /stats/{shortCode}

**Response**

```json
{
  "shortCode": "example",
  "longUrl": "https://example.com",
  "clickCount": 5,
  "createdAt": "2026-01-19T18:45:12Z",
  "expiresAt": "2026-01-19T19:00:12Z",
  "expired": false
}
```

---

## 🧪 Running Locally

### Prerequisites

- Java 17
- PostgreSQL
- Maven

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will run on:

```
http://localhost:8080
```

---

## 🌍 Deployment

The application is ready for cloud deployment on platforms such as:

- Railway
- Render
- AWS
- Heroku

### Features

- Uses environment variables for database configuration
- Dynamically generates short URLs based on request host
- No hardcoded localhost URLs

---

## 🧠 Design Notes

- Controller handles HTTP concerns
- Service layer contains business logic
- Repository layer handles database access
- Uses proper HTTP status codes:
    - 302 – Redirect
    - 404 – Not Found
    - 410 – Gone
    - 409 – Conflict
- Expiry handled using time-based checks
- Click count increments only for valid redirects

---

## 📈 Future Improvements

- Rate limiting
- Redis caching for frequently accessed links
- Authentication for private URLs
- Frontend interface (Flutter / React)


