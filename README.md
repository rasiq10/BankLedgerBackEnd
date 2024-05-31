The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)


To run this server locally, do the following:

1. **Prerequisites:**
    - Ensure you have Java JDK 11 or newer installed.
    - Install Maven for dependency management and project building.

3. **Navigate to the Project Directory:**
    - Change into the project directory with `cd BankLedgerApplication`.

4. **Build the Project:**
    - Run `mvn clean install` to build the project and install the dependencies.

5. **Run the Application:**
    - Execute `mvn spring-boot:run` to start the application.
    - The server will start on `localhost:8080` by default.

6. **Access the Application:**
    - Open your browser and navigate to `http://localhost:8080` to view the application.



- **Single Instance Usage:** The application is intended to run as a single instance without the need for distributed deployment initially.
- **In-Memory Database:** Transactions are stored in-memory and not persisted to a physical database, assuming restarts and data persistence aren't immediate concerns.
- **Concurrency Handling:** It is assumed that the application will handle moderate concurrent requests without needing advanced concurrency management or distributed locking mechanisms.
- **User Authentication:** User authentication and authorization are outside the scope of this service; it is assumed there's an external system handling this.



If I were to scale this application, I would opt for a cloud-based approach using technologies such as:

- **AWS EC2 or Azure VMs:** For hosting the application, ensuring scalability and reliability.
- **Docker Containers:** Package the application in Docker containers to ensure consistency across different environments and ease of deployment.
- **Kubernetes:** For orchestration of containerized applications, improving scalability and fault tolerance.
- **CI/CD Pipelines:** Implement continuous integration and continuous deployment using Jenkins to automate build and deployment processes.

