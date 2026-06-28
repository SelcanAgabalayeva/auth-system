# 🔐 Auth System - Spring Boot JWT Authentication

## 📌 Project Overview

Auth System is a secure backend authentication system built with **Spring Boot**.

The project provides complete authentication functionality including user registration, login, JWT-based authorization, refresh token management, logout and protected user endpoints.

The main goal of this project is to implement a production-style authentication flow using **Access Token + Refresh Token architecture**.

---

# 🚀 Features

✅ User Registration  
✅ User Login  
✅ Password encryption with BCrypt  
✅ JWT Access Token generation  
✅ Refresh Token generation and rotation  
✅ Refresh Token expiration handling  
✅ Logout functionality  
✅ Protected API endpoints  
✅ JWT Authentication Middleware(Filter)  
✅ Current authenticated user endpoint  
✅ Global exception handling  
✅ DTO based response structure  

---

# 🛠 Technologies

- Java 17
- Spring Boot 3
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- ModelMapper
- Maven

---

# 🔐 Authentication Flow

## 1. Register

### Endpoint

```
POST /api/auth/register
```

### Description

Creates a new user account.

During registration:

- Password is encrypted using BCrypt
- Access token is generated
- Refresh token is created and stored in database


### Response Example

```json
{
    "success": true,
    "message": "Registration successful",
    "accessToken": "jwt_access_token",
    "refreshToken": "random_refresh_token",
    "expiresIn": 900
}
```

---

# 2. Login

### Endpoint

```
POST /api/auth/login
```

### Description

Authenticates user credentials and generates new tokens.

Flow:

1. Find user by email
2. Check password
3. Validate account status
4. Delete old refresh token
5. Create new refresh token
6. Generate JWT access token


### Response Example

```json
{
    "success": true,
    "message": "Login successful",
    "accessToken": "jwt_access_token",
    "refreshToken": "random_refresh_token",
    "expiresIn": 900
}
```

---

# 3. Refresh Token

### Endpoint

```
POST /api/auth/refresh-token
```

### Request Body

```json
{
    "refreshToken": "refresh_token"
}
```

### Description

Creates a new access token when the old access token expires.

Flow:

- Validate refresh token
- Check token existence
- Check expiration date
- Delete expired refresh token
- Find user
- Generate new access token
- Delete old refresh token
- Create new refresh token


### Successful Response

```json
{
    "success": true,
    "message": "Token refreshed successfully",
    "accessToken": "new_access_token",
    "refreshToken": "new_refresh_token",
    "expiresIn": 900
}
```

---

# 4. Logout

### Endpoint

```
POST /api/auth/logout
```

### Request Body

```json
{
    "refreshToken": "refresh_token"
}
```

### Description

Logout removes refresh token from database.

JWT access tokens are stateless, therefore frontend removes the access token locally.


### Response

```json
{
    "success": true,
    "message": "Logged out successfully"
}
```

---

# 5. Get Current User

## Protected Endpoint

```
GET /api/auth/me
```

### Headers

```
Authorization: Bearer ACCESS_TOKEN
```

### Description

Returns currently authenticated user information.

Password hash is never returned.


### Response

```json
{
    "success": true,
    "user": {
        "id": 1,
        "username": "selcan",
        "email": "selcan@gmail.com",
        "createdAt": "2026-06-29T00:00:00"
    }
}
```

---

# 🔑 JWT Configuration

## Access Token

Expiration:

```
15 minutes
```

Used for accessing protected endpoints.

---

## Refresh Token

Expiration:

```
7 days
```

Stored in database and used to generate new access tokens.

---

# 📂 Project Structure

```
src/main/java/com/selcan/auth_system

├── controller
│
├── service
│   └── impls
│
├── repositories
│
├── entity
│
├── dto
│
├── security
│   ├── JwtService
│   ├── JwtAuthenticationFilter
│   └── SecurityConfig
│
└── exception
```

---

# 🛡 Security Implementation

The project uses Spring Security with JWT authentication.

Authentication flow:

```
Client
  |
  |
Authorization Header
(Bearer JWT Token)
  |
  |
JwtAuthenticationFilter
  |
  |
Validate JWT
  |
  |
SecurityContext
  |
  |
Protected Controller
```

---

# 🧪 API Testing

All endpoints were tested using Postman.

Tested scenarios:

✅ Register user  
✅ Login user  
✅ Generate access token  
✅ Generate refresh token  
✅ Refresh token with valid token  
✅ Invalid refresh token returns 401  
✅ Expired refresh token deletion  
✅ Logout functionality  
✅ Protected `/me` endpoint  
✅ Request without token returns 401  
✅ Expired JWT handling  
✅ Invalid JWT handling  

---

# ⚙️ Running the Project

## Clone repository

```bash
git clone repository-url
```

## Enter project folder

```bash
cd auth-system
```

## Run application

```bash
mvn spring-boot:run
```

---

# ⚙️ Environment Configuration

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/auth_system

spring.datasource.username=postgres

spring.datasource.password=password


jwt.secret=your_secret_key

jwt.expiration=900000
```

---

# 📦 Database Tables

## users

Stores user information:

- id
- username
- email
- password_hash
- is_active
- created_at


## refresh_tokens

Stores refresh tokens:

- id
- user_id
- token
- expires_at
- created_at

---

# 👩‍💻 Author

**Selcan Ağabalayeva**

Backend Developer | Java Spring Boot
