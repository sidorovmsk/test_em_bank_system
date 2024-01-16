# Task Management System

This project is a simple Task Management System built with Spring Boot. It includes Swagger UI for easy API interaction.
Check the `task.txt` file in the project root for the full details of the test assignment.

## Local Development Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/sidorovmsk/test_em.git
    cd test_em
    ```

2. **Run the application:**

    ```bash
    docker-compose up
    ```

   This will start all required services, including the database, and your Spring Boot application.

3. **Swagger UI:**

   After starting, Swagger UI will be accessible
   at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

   You can use Swagger UI to interact with your API and view documentation.

4. **Stop the application:**

   To stop the application and all related services, use:

    ```bash
    docker-compose down
    ```

   This will stop the containers and free up resources.

## Additional Configurations

- **PostgreSQL:**

  The project uses a PostgreSQL database. Adjust database settings in `application.yaml` if needed.

- **Additional Environment Variables:**

  You can configure other environment variables, such as the application port and other parameters, in the `.env` file.

## Notes

- Ensure that ports 8080 and 5432 (default) are not being used by other processes on your computer.

- Make sure Docker and Docker Compose are installed on your computer.
