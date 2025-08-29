# Users Database Application

A Spring Boot application that aggregates user data from multiple databases and provides a unified REST API endpoint.

## Features

- **Multi-Database Support**: Supports PostgreSQL, MySQL, and Oracle databases
- **Declarative Configuration**: YAML-based configuration for data sources
- **REST API**: Single endpoint for retrieving aggregated user data
- **OpenAPI Documentation**: Swagger UI for API documentation
- **Filtering**: Optional query parameters for filtering users
- **Connection Pooling**: HikariCP for efficient database connections
- **Async Processing**: Parallel data fetching from multiple databases
- **Integration Tests**: Testcontainers-based integration testing

## API Documentation

Once the application is running, you can access:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI Spec**: http://localhost:8080/api-docs

## Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- Docker and Docker Compose

## Configuration

### Data Sources Configuration

Create or modify `src/main/resources/data-sources.yml`:

```yaml
data-sources:
  - name: data-base-1
    strategy: postgres  # Optional: postgres, mysql, oracle
    url: jdbc:postgresql://localhost:5432/db1
    table: users
    user: testuser
    password: testpass
    mapping:
      id: user_id
      username: login
      name: first_name
      surname: last_name
  
  - name: data-base-2
    strategy: postgres
    url: jdbc:postgresql://localhost:5433/db2
    table: user_table
    user: testuser
    password: testpass
    mapping:
      id: ldap_login
      username: ldap_login
      name: name
      surname: surname
```

### Field Mapping

The `mapping` section defines how database columns map to the unified User model:
- `id`: Unique user identifier column
- `username`: Username column
- `name`: First name column
- `surname`: Last name column

## Running the Application

### Method 1: Using Docker Compose

1. **Build the application**:
   ```bash
   mvn clean package -DskipTests
   ```

3. **Start with Docker Compose**:
   ```bash
   docker-compose up --build
   ```

This will start:
- The application on port 8080
- PostgreSQL database 1 on port 5432
- PostgreSQL database 2 on port 5433

## API Usage

### Get All Users

```bash
curl -X GET "http://localhost:8080/users" \
     -H "accept: application/json"
```

**Response:**
```json
[
  {
    "id": "example-user-id-1",
    "username": "user-1",
    "name": "User",
    "surname": "Userenko"
  },
  {
    "id": "example-user-id-2",
    "username": "user-2",
    "name": "Testuser",
    "surname": "Testov"
  }
]
```

### Filter Users by Id

```bash
curl -X GET "http://localhost:8080/users?id=example-user-id-1" \
     -H "accept: application/json"
```


### Filter Users by Name

```bash
curl -X GET "http://localhost:8080/users?name=John" \
     -H "accept: application/json"
```

### Filter Users by Username

```bash
curl -X GET "http://localhost:8080/users?username=user-1" \
     -H "accept: application/json"
```

### Filter Users by Surname

```bash
curl -X GET "http://localhost:8080/users?surname=Doe" \
     -H "accept: application/json"
```

### Combined Filters

```bash
curl -X GET "http://localhost:8080/users?username=user-1&surname=Userenko" \
     -H "accept: application/json"
```