# CONSPHERE — Social Media Platform with Custom Content Filters

**CONSPHERE** is a full-stack social media application developed using **Java 21, Spring Boot 3, Spring Security, JWT, JPA, MySQL**, and **React (Vite + Tailwind CSS)**.

Unlike generic social platforms, CONSPHERE introduces a unique **Filter System**: users can construct custom content environments by grouping between 3 and 10 hashtags. In addition to standard home feeds, users can switch feeds to view posts filtered dynamically by these hashtag environments.

---

## 🚀 Key Features

1. **User Authentication & Authorization**:
   - Secure User Registration & Login with BCrypt password hashing.
   - Stateless JWT authentication via `Bearer` tokens.
   - User Profile management (updating name, email, profile picture URL, mobile number).

2. **Post Management & Automatic Hashtag Extraction**:
   - Create, edit, and delete posts with captions and optional image URLs.
   - **Automatic Hashtag Extractor**: Captions are parsed with Java Regular Expressions (`#[a-zA-Z0-9_]+`). Extracted hashtags are normalized and saved as relational `Hashtag` entities automatically.

3. **Social Interactions**:
   - **Likes**: Users can like/unlike posts (unique constraint prevents double likes).
   - **Comments**: Users can post, update, and delete comments on posts.
   - **Follow System**: Users can follow and unfollow other creators, tracking followers and following counts.

4. **Filter Engine (Core Differentiator)**:
   - **Built-in Filters**: Pre-seeded by the backend on startup (`Programming`, `Education`, `Entertainment`, `Sports`, `Fitness`, `Technology`, `Gaming`, `Music`, `Movies`, `News`).
   - **Custom Filters**: Users can create, edit, and delete custom filters containing between **3 and 10 hashtags**.
   - **Filter Feed**: Fetches posts containing ANY of the filter's hashtags in descending chronological order.

---

## 🛠️ Technology Stack

### Backend
- **Language**: Java 21
- **Framework**: Spring Boot 3.2.5
- **Security**: Spring Security 6 with custom JWT Filter (`jjwt 0.11.5`)
- **Database**: MySQL 8.0+ (Fallback to H2 database for rapid testing)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Apache Maven 3.9.9

### Frontend
- **Framework**: React 18
- **Build Tool**: Vite 5
- **Styling**: Tailwind CSS 3 with custom glassmorphism components
- **Routing**: React Router DOM v6
- **Icons**: Lucide React

---

## 📁 Project Architecture

```
CONSPHERE/
├── backend/
│   ├── src/main/java/com/project/consphere/
│   │   ├── config/              # SecurityConfig, JwtService, JwtFilter, DataInitializer
│   │   ├── controller/          # REST Endpoints (Auth, User, Post, Comment, Like, Follow, Filter)
│   │   ├── dto/                 # Request and Response DTOs
│   │   ├── exception/           # Custom Exceptions & GlobalExceptionHandler
│   │   ├── model/               # JPA Entities (User, Post, Comment, Like, Follow, Filter, Hashtag)
│   │   ├── repository/          # Spring Data JPA Repositories
│   │   └── service/             # Business Logic & Implementations
│   └── src/main/resources/
│       └── application.properties
├── frontend/
│   ├── src/
│   │   ├── components/          # Navbar, PostCard, CommentSection, LikeButton, FollowButton, FilterCard, CreateFilterModal
│   │   ├── context/             # AuthContext (React Auth State)
│   │   ├── pages/               # Home, Login, Register, Profile, CreatePost, EditProfile, FiltersPage
│   │   └── services/            # Axios API wrappers (authService, postService, commentService, etc.)
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
└── README.md
```

---

## 🚦 Getting Started & Execution Guide

### Prerequisites
- Java 21 JDK installed (`java -version`)
- Node.js (v18+) & npm installed (`npm -version`)
- MySQL Server running locally on port `3306` (Optional: if MySQL is offline, configure `h2` in `application.properties`).

---

### Step 1: Run the Backend

1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Configure MySQL credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/consphere_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=your_password
   ```
3. Compile and launch the Spring Boot application:
   ```bash
   mvn clean spring-boot:run
   ```
4. The backend will start on **`http://localhost:8080`** and populate the built-in filters automatically.

---

### Step 2: Run the Frontend

1. Open a new terminal tab and navigate to the `frontend` directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the Vite development server:
   ```bash
   npm run dev
   ```
4. Access the web application at **`http://localhost:5173`**.

---

## 📡 REST API Reference & Postman Guide

### 1. Authentication Endpoints (`/auth`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/auth/register` | Register a new user | ❌ No |
| `POST` | `/auth/login` | Login and obtain JWT token | ❌ No |

#### `POST /auth/register` Payload Example:
```json
{
  "username": "gowtham",
  "email": "gowtham@example.com",
  "password": "password123",
  "confirmPassword": "password123",
  "firstName": "Gowtham",
  "lastName": "Nama",
  "mobileNumber": "+919876543210"
}
```

#### `POST /auth/login` Payload Example:
```json
{
  "username": "gowtham",
  "password": "password123"
}
```

---

### 2. User Profile Endpoints (`/users`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/users/me` | Fetch logged-in user profile | 🔒 Yes |
| `PATCH` | `/users/updateUser` | Update profile information | 🔒 Yes |
| `GET` | `/users/{username}` | Fetch user profile & stats by username | ❌ Optional |

#### `PATCH /users/updateUser` Payload Example:
```json
{
  "firstName": "Gowtham",
  "lastName": "N.",
  "profilePicURL": "https://images.unsplash.com/photo-1534528741775-53994a69daeb",
  "mobileNumber": "+919876543210"
}
```

---

### 3. Post Endpoints (`/posts`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/posts` | Create new post (extracts hashtags automatically) | 🔒 Yes |
| `GET` | `/posts/feed` | Get global chronological home feed | ❌ Optional |
| `GET` | `/posts/my-posts` | Get logged-in user's posts | 🔒 Yes |
| `GET` | `/posts/user/{username}` | Get posts published by specific user | ❌ Optional |
| `GET` | `/posts/{id}` | Get post details by ID | ❌ Optional |
| `PUT` | `/posts/{id}` | Update post caption or image URL | 🔒 Yes |
| `DELETE` | `/posts/{id}` | Delete post | 🔒 Yes |

#### `POST /posts` Payload Example:
```json
{
  "caption": "Excited to launch CONSPHERE built using #java #springboot and #react! Placement season ready #dsa",
  "imageUrl": "https://images.unsplash.com/photo-1517694712202-14dd9538aa97"
}
```

---

### 4. Filter Endpoints (`/filters`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `GET` | `/filters` | List all built-in & accessible custom filters | ❌ Optional |
| `GET` | `/filters/built-in` | List pre-seeded built-in filters | ❌ Optional |
| `GET` | `/filters/my-filters` | List custom filters owned by user | 🔒 Yes |
| `POST` | `/filters` | Create custom filter (3-10 hashtags required) | 🔒 Yes |
| `GET` | `/filters/{id}/feed` | Fetch post feed matching filter's hashtags | ❌ Optional |
| `PUT` | `/filters/{id}` | Update custom filter | 🔒 Yes |
| `DELETE` | `/filters/{id}` | Delete custom filter | 🔒 Yes |

#### `POST /filters` Payload Example:
```json
{
  "name": "Placement Prep & Coding",
  "description": "Custom environment for DSA, SpringBoot, and interview prep",
  "hashtags": ["java", "springboot", "dsa", "leetcode"]
}
```

---

### 5. Comment Endpoints (`/comments`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/comments/post/{postId}` | Add a comment to a post | 🔒 Yes |
| `GET` | `/comments/post/{postId}` | Get comments for a post | ❌ Optional |
| `PUT` | `/comments/{commentId}` | Edit comment | 🔒 Yes |
| `DELETE` | `/comments/{commentId}` | Delete comment | 🔒 Yes |

#### `POST /comments/post/1` Payload Example:
```json
{
  "content": "Awesome project structure! Really clean Java code."
}
```

---

### 6. Like Endpoints (`/likes`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/likes/post/{postId}` | Like a post | 🔒 Yes |
| `DELETE` | `/likes/post/{postId}` | Unlike a post | 🔒 Yes |
| `GET` | `/likes/post/{postId}` | Check like status & count for a post | ❌ Optional |

---

### 7. Follow Endpoints (`/follow`)

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/follow/{username}` | Follow a user | 🔒 Yes |
| `DELETE` | `/follow/{username}` | Unfollow a user | 🔒 Yes |
| `GET` | `/follow/followers` | Get followers of logged-in user | 🔒 Yes |
| `GET` | `/follow/following` | Get users followed by logged-in user | 🔒 Yes |

---

## 🎓 Design Philosophy & Code Style

- **Human-Readable Java Code**: Written in a clean, straightforward style suitable for a B.Tech CSE Student Capstone Project.
- **No Over-engineering**: Clear separation between `Model`, `Repository`, `Service`, and `Controller` layers without unnecessary abstractions.
- **Robust DTO Mapping**: Prevents circular JSON reference errors (`Jackson` recursion) when serializing entity relationships.
- **Dynamic Frontend UI**: Built with React Hooks and Axios, providing instant feedback (Optimistic Likes, Filter feeds, Live preview).

---

## 📄 License
This project is open-source and available under the **MIT License**.
