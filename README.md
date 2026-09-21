# Vehicle Rental & Booking System - Microservices

A distributed Vehicle Rental & Booking System built with **Spring Boot microservices**. The system is split into 8 independent services that register with a central **Eureka Server** and communicate with each other using **OpenFeign**.

## Architecture

```
                    +------------------+
                    |  Eureka Server   |
                    | (Service Registry)|
                    +---------+--------+
                              |
   +---------+---------+------+---+---------+---------+---------+
   |         |         |          |         |         |         |
Customer  Vehicle    Driver    Booking    Trip     Payment  Feedback
Service   Service    Service   Service   Service   Service   Service
   |         |         |          |         |         |         |
   +---------+---------+------+---+---------+---------+---------+
                              |
                            MySQL
```

## Services

| Service | Responsibility |
|---|---|
| **eureka-server** | Service discovery and registry for all microservices |
| **customer-service** | Manages customer records and profiles |
| **vehicle-service** | Manages the vehicles available for rent |
| **driver-service** | Manages drivers and their details |
| **booking-service** | Handles vehicle booking requests |
| **trip-service** | Manages trips created from bookings |
| **payment-service** | Handles payments for bookings |
| **feedback-service** | Collects customer feedback |

## Tech Stack

- **Java**, **Spring Boot**
- **Spring Cloud Netflix Eureka** (service discovery)
- **Spring Cloud OpenFeign** (inter-service communication)
- **MySQL** (database)
- **Maven** (build tool)

## Prerequisites

- JDK 17 or higher
- Maven (or use the included `mvnw` wrapper)
- MySQL running locally

## How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/UdayChourasiya/Vehicle-Rental-Booking-System-Microservices.git
   cd Vehicle-Rental-Booking-System-Microservices
   ```

2. **Configure the database**
   Open `src/main/resources/application.properties` in each service and set your MySQL URL, username and password.

3. **Start Eureka Server first**
   ```bash
   cd eureka-server
   mvnw spring-boot:run
   ```
   Eureka dashboard (default port): http://localhost:8761

4. **Start the remaining services** (each in its own terminal)
   ```bash
   cd customer-service
   mvnw spring-boot:run
   ```
   Repeat for `vehicle-service`, `driver-service`, `booking-service`, `trip-service`, `payment-service` and `feedback-service`.

5. Open the Eureka dashboard and confirm all services show as **UP**.

## Project Structure

```
Vehicle-Rental-Booking-System-Microservices/
├── eureka-server/
├── customer-service/
├── vehicle-service/
├── driver-service/
├── booking-service/
├── trip-service/
├── payment-service/
└── feedback-service/
```

## Author

**Uday Chourasiya**
GitHub: [UdayChourasiya](https://github.com/UdayChourasiya)
