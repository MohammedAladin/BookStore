# Bookstore API

Bookstore API is a RESTful API developed using Java Spring that allows customers to browse, search, and purchase books. It integrates with a payment gateway to process payments and uses Spring Security for authentication and authorization.

## Requirements

To run and use the Bookstore API, you will need the following:

- Java 8 or higher
- MySQL or PostgreSQL database
- PayPal or Stripe account for payment gateway integration

## Setup

1. Clone the repository to your local machine.
2. Create a MySQL or PostgreSQL database and update the `application.properties` file with the database information.
3. Create a PayPal or Stripe account and update the `application.properties` file with the payment gateway information.
4. Run the following command in the terminal to start the application:

```bash
./mvnw spring-boot:run
