# Bookstore API

Bookstore API is a RESTful API developed using Java Spring that allows customers to browse, search, and purchase books. It integrates with a payment gateway to process payments and uses Spring Security for authentication and authorization.

## Requirements

To run and use the Bookstore API, you will need the following:

- Java 8 or higher
- MySQL database
- PayPal account for payment gateway integration

## Setup

1. Clone the repository to your local machine.
2. Create a MySQL database and update the `application.properties` file with the database information.
3. Create a PayPal account and update the `application.properties` file with the payment gateway information.
4. Run the following command in the terminal to start the application:

```bash
./mvnw spring-boot:run
```
## API Endpoints
The application will be available at `http://localhost:8080`.

### GET /books

Returns a list of books.

Parameters:
- `author` (optional): filter by author name
- `title` (optional): filter by book title
- `genre` (optional): filter by book genre
- `rating` (optional): filter by book rating

### GET /books/{id}

Returns detailed information about a book with the specified ID.
