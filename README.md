# E-Commerce Microservices Application

This is a Java-based microservices application for an e-commerce platform. The application is built using Spring Boot and Maven. It uses MySQL as the database and Kafka for event-driven architecture. The application also includes resilience patterns like Circuit Breaker, Retry, and Time Limiter using Resilience4j.

## Services

The application is composed of multiple services:

1. **Order Service**: Handles all operations related to orders. It communicates with the Inventory Service to check the availability of items. It also publishes a message to a Kafka topic when an order is placed.

2. **Inventory Service**: Manages the inventory of products. It provides an API to check the availability of items.

3. **Product Service**: Manages the product catalog. It provides APIs to create, update, delete, and retrieve products.

4. **Notification Service**: Handles all operations related to notifications. It listens to the Kafka topic and sends notifications when an order is placed.

5. **API Gateway**: Acts as a single entry point for all client requests. It routes requests to appropriate microservices. It also handles security using Spring Security and OAuth2.

## Service Discovery

Service discovery is implemented using Eureka. Each microservice registers itself with Eureka on startup and deregisters on shutdown. This allows the services to discover each other and communicate.

## API Gateway

The API Gateway is the single entry point for all client requests. It handles request routing, composition, and protocol translation. It uses Spring Security and OAuth2 for security.

## Kafka Messaging

Kafka is used for event-driven architecture. When an order is placed, a message is published to a Kafka topic. This allows for asynchronous communication between services and helps to decouple them.

## Setup and Run

1. Ensure you have Java 11 and Maven installed on your system.

2. Clone the repository.

3. Navigate to the root directory of the project and run the following command to build all the services:

    ```bash
    mvn clean install
    ```

4. Start each service individually by navigating to the service directory and running the following command:

    ```bash
    mvn spring-boot:run
    ```

## Configuration

Each service has its own configuration in the `application.properties` file. Here you can set up the database connection, server port, Kafka configuration, and other service-specific settings.

## Endpoints

Here are some of the key endpoints:

- Order Service:
  - POST `/api/order`: Create a new order.
  - GET `/api/order/{id}`: Get an order by ID.

- Inventory Service:
  - GET `/api/inventory`: Get the inventory for a list of SKU codes.

- Product Service:
  - POST `/api/product`: Create a new product.
  - GET `/api/product/{id}`: Get a product by ID.

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License.
