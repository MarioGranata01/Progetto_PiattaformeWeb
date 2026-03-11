# 🛒 E-commerce Platform

## 📝 Overview
A full-stack E-commerce web application. The **Frontend** is built with Angular 17, providing a dynamic and responsive user experience. The **Backend** handles user authentication, product management, and order processing using a robust Spring Boot architecture.

## 🛠️ Tech Stack
* **Frontend:** Angular 17, TypeScript, HTML/CSS
* **Backend:** Java, Spring Boot
* **Database:** MySQL

## ✨ Features
* Product catalog and search.
* Shopping cart management.
* Secure user authentication and authorization.
* Responsive design for desktop.

## 🚀 Setup & Installation

### Prerequisites
* Node.js & npm
* Angular CLI
* Java JDK
* MySQL Server installed and running

### Frontend Setup
1. Navigate to the frontend directory:
   ```bash
   cd Frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Run the development server:
   ```bash
   ng serve
   ```
   *(Navigate to `http://localhost:4200/` in your browser. The application will automatically reload if you change any of the source files).*

### Backend Setup
1. Navigate to the backend directory:
   ```bash
   cd Backend
   ```
2. Ensure your MySQL database is running and update the `application.properties` file with your credentials.
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
