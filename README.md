**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

You are tasked with building a simple bank ledger system that utilizes the [event sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) pattern to maintain a transaction history. The system should allow users to perform basic banking operations such as depositing funds, withdrawing funds, and checking balances. The ledger should maintain a complete and immutable record of all transactions, enabling auditability and reconstruction of account balances at any point in time.

## Details
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host.

The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.


Implement the event sourcing pattern to record all banking transactions as immutable events. Each event should capture relevant information such as transaction type, amount, timestamp, and account identifier.
Define the structure of events and ensure they can be easily serialized and persisted to a data store of your choice. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

Here’s a breakdown of the key criteria we’ll be considering when grading your submission:

**Adherence to Design Patterns:** We’ll evaluate whether your implementation follows established design patterns such as following the event sourcing model.

**Correctness**: We’ll assess whether your implementation effectively implements the desired pattern and meets the specified requirements.

**Testing:** We’ll assess the comprehensiveness and effectiveness of your test suite, including unit tests, integration tests, and possibly end-to-end tests. Your tests should cover critical functionalities, edge cases, and potential failure scenarios to ensure the stability of the system.

**Documentation and Clarity:** We’ll assess the clarity of your documentation, including comments within the code, README files, architectural diagrams, and explanations of design decisions. Your documentation should provide sufficient context for reviewers to understand the problem, solution, and implementation details.

# Candidate README
## Bootstrap instructions
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


## Design Considerations

I decided to build the Bank Ledger Application using Spring Boot for several reasons:

- **Simplicity and Rapid Development:** Spring Boot provides a lot of auto-configuration and starter dependencies which help in setting up a project quickly and running within minutes.
- **Scalability:** Using Spring Boot makes it easier to scale the application both vertically and horizontally.

## Assumptions

In designing the Bank Ledger Service, the following assumptions were made:

- **Single Instance Usage:** The application is intended to run as a single instance without the need for distributed deployment initially.
- **In-Memory Database:** Transactions are stored in-memory and not persisted to a physical database, assuming restarts and data persistence aren't immediate concerns.
- **Concurrency Handling:** It is assumed that the application will handle moderate concurrent requests without needing advanced concurrency management or distributed locking mechanisms.
- **User Authentication:** User authentication and authorization are outside the scope of this service; it is assumed there's an external system handling this.


## Bonus: Deployment Considerations

If I were to deploy this application, I would opt for a cloud-based approach using technologies such as:

- **AWS EC2 or Azure VMs:** For hosting the application, ensuring scalability and reliability.
- **Docker Containers:** Package the application in Docker containers to ensure consistency across different environments and ease of deployment.
- **Kubernetes:** For orchestration of containerized applications, improving scalability and fault tolerance.
- **CI/CD Pipelines:** Implement continuous integration and continuous deployment using Jenkins to automate build and deployment processes.


## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/189f38f3-ccb6-4aca-a174-8b49c4ba0d0e" target="_blank">this screen</a>.