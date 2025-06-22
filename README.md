# E-commerce Inventory Management API

A simple Spring Boot REST API to manage item inventory, including adding items, supplying stock, reserving items, and checking availability.

## Table of Contents
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [How to Run](#how-to-run)
- [API Endpoints](#api-endpoints)
    - [Item Management](#item-management)
    - [Inventory & Stock](#inventory--stock)
    - [Reservations](#reservations)

---

## Architecture

This project follows a standard 3-tier architecture for a Spring Boot application:

-   **Controller (`ItemController.java`)**: The presentation layer that handles incoming HTTP requests, validates them, and maps them to the appropriate service methods. It's responsible for all API routing.
-   **Service (`ItemServiceImpl.java`)**: The business logic layer. It coordinates the application's operations, processing data and performing calculations. It acts as an intermediary between the Controller and the Repository.
-   **Repository (`ItemRepository.java`)**: The data access layer. It communicates directly with the database (e.g., H2, MySQL, PostgreSQL) to perform CRUD (Create, Read, Update, Delete) operations on the data models.

This separation of concerns makes the application modular, easier to test, and maintain.

---

## Prerequisites

-   Java Development Kit (JDK) 11 or newer
-   Apache Maven
-   An IDE like IntelliJ IDEA or VS Code (optional)
-   A tool for API testing, like [Postman](https://www.postman.com/) or `curl`.

---

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd <your-project-directory>
    ```

2.  **Build the project using Maven:**
    This command will compile the code and download all the necessary dependencies.
    ```bash
    mvn clean install
    ```

3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

The application will start on the default port `8080`. You should see the Spring Boot banner in your console, indicating that the server is running.

---

## API Endpoints

Here is a detailed breakdown of all the available API endpoints.

### Item Management

#### 1. Add a New Item
Adds a new item to the system's inventory.

-   **URL:** `/addItem`
-   **Method:** `POST`
-   **Request Body:**
    ```json
    {
        "name": "Laptop Pro",
        "price": 1200.00
    }
    ```
-   **Success Response (200 OK):**
    ```
    item created with id: 1
    ```
-   **Curl Example:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"name": "Laptop Pro", "price": 1200.00}' http://localhost:8080/addItem
    ```

#### 2. Get All Items
Retrieves a list of all items currently in the inventory.

-   **URL:** `/getAll`
-   **Method:** `GET`
-   **Success Response (200 OK):**
    ```json
    [
        {
            "id": 1,
            "name": "Laptop Pro",
            "price": 1200.00,
            "quantity": 50
        },
        {
            "id": 2,
            "name": "Wireless Mouse",
            "price": 25.50,
            "quantity": 200
        }
    ]
    ```
-   **Curl Example:**
    ```bash
    curl http://localhost:8080/getAll
    ```

### Inventory & Stock

#### 1. Supply an Item
Increases the stock for a specific item by its ID.

-   **URL:** `/supply`
-   **Method:** `POST`
-   **Request Body:**
    ```json
    {
        "item": {
            "id": 1
        },
        "quantity_suppied": 50
    }
    ```
-   **Success Response (200 OK):**
    ```
    Item id with 1 is supplied
    ```
-   **Error Response (400 Bad Request):**
    ```
    Supply couldn't be done
    ```
-   **Curl Example:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"item":{"id":1}, "quantity_suppied":50}' http://localhost:8080/supply
    ```

#### 2. Get Item Availability
Checks the current available quantity of a specific item.

-   **URL:** `/items/{itemId}/availability`
-   **Method:** `GET`
-   **Path Variable:** `itemId` - The unique ID of the item.
-   **Success Response (200 OK):**
    ```
    The available amount is: 100
    ```
-   **Error Response (400 Bad Request):**
    ```
    The item is not available
    ```
-   **Curl Example:**
    ```bash
    curl http://localhost:8080/items/1/availability
    ```

### Reservations

#### 1. Reserve an Item
Creates a reservation for an item if enough quantity is available.

-   **URL:** `/reserve`
-   **Method:** `POST`
-   **Request Body:**
    ```json
    {
        "item": {
            "id": 1
        },
        "quantity": 5
    }
    ```
-   **Success Response (200 OK):**
    ```
    Item Laptop Pro is reserved with reservation Id: 101
    ```
-   **Error Response (400 Bad Request):**
    ```
    Item is not available for reservation
    ```
-   **Curl Example:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"item":{"id":1}, "quantity":5}' http://localhost:8080/reserve
    ```

#### 2. Cancel a Reservation
Cancels an existing item reservation using its reservation ID.

-   **URL:** `/cancel-reservation`
-   **Method:** `POST`
-   **Request Body:**
    ```json
    {
        "reservation_id": 101
    }
    ```
-   **Success Response (200 OK):**
    ```
    Required reservation is cancelled
    ```
-   **Error Response (400 Bad Request):**
    ```
    Required reservation can't be cancelled
    ```
-   **Curl Example:**
    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{"reservation_id": 101}' http://localhost:8080/cancel-reservation
    ```