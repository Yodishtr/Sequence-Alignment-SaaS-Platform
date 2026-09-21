# Sequence Alignment Service

A work-in-progress Spring Boot backend for submitting and tracking bioinformatics sequence-alignment jobs. The project is designed to place tools such as NCBI BLAST and Smith-Waterman behind a multi-tenant API with persistent job metadata and configurable sequence storage.

The repository currently implements the domain model, persistence layer, request and response objects, mapping utilities, exception handling, local and Amazon S3 storage adapters, automated tests, and a GitHub Actions build. The REST endpoints, background execution pipeline, and native alignment-tool integration are still under development.

## Why I am building this

Bioinformatics command-line tools are powerful, but an application that exposes them as a service must solve more than the alignment itself. It needs to:

- Separate data belonging to different tenants
- Validate job requests
- Store potentially large sequence inputs outside the main database
- Track job state and execution metadata
- Return stable resource identifiers
- Handle failures consistently
- Support local development and cloud-oriented storage
- Test persistence and infrastructure code independently

This project is my way of learning how those concerns fit together in a production-style Spring Boot backend.

## Current status

### Implemented

- Multi-tenant JPA domain model
- UUID identifiers for tenants, API keys, and alignment jobs
- Spring Data JPA repositories
- DTOs for job submission, job responses, and errors
- Request-to-entity and result-to-response mappers
- Local filesystem storage adapter
- Amazon S3 storage adapter
- Property-based selection between local and S3 storage
- Central exception-response structure
- Persistence tests with H2 and `@DataJpaTest`
- Unit tests for DTOs, configuration, storage, and exception handling
- Maven build and GitHub Actions workflow

### In progress

- Job orchestration service
- REST controllers
- Request validation and tenant resolution
- Background job execution
- NCBI BLAST integration
- Smith-Waterman integration
- PostgreSQL migrations
- End-to-end API tests

## Planned request flow

The intended job lifecycle is:

```text
Submit request
      |
      v
Validate tenant and input
      |
      v
Store sequence in local storage or S3
      |
      v
Create a pending alignment job in PostgreSQL
      |
      v
Run the selected alignment tool asynchronously
      |
      v
Store result metadata and update job status
      |
      v
Poll the API for status and results
```

This flow describes the target architecture. The storage and persistence foundations exist, but the complete HTTP and execution pipeline has not yet been implemented.

## Domain model

### Tenant

Represents an organization or user boundary. A tenant owns API keys and alignment jobs and includes a quota value for future usage enforcement.

### ApiKey

Represents an API credential associated with one tenant. The entity stores identifying and lifecycle information needed for future request authentication.

### AlignmentJob

Represents a submitted alignment task. It tracks information such as:

- UUID job identifier
- Selected alignment tool
- Job status
- Input reference
- Timestamps
- Owning tenant
- Parameters, execution metrics, and result summary

The current tool model includes BLAST and Smith-Waterman. Job states represent the lifecycle from submission through completion or failure.

## Storage design

Sequence data is stored through the `StorageUtility` interface instead of being written directly by the service layer:

```java
public interface StorageUtility {
    String saveSequence(String sequenceData, String targetDatabase, UUID jobIdentifier);
    String readSequence(String inputReference);
    boolean deleteSequence(String inputReference);
    boolean exists(String inputReference);
}
```

This allows the application to choose an implementation through configuration.

### Local storage

When `file.storage.type=local`, `LocalStorage`:

- Creates the configured root directory during application startup
- Saves sequence data under a job-specific filename
- Returns the resulting file path as the input reference
- Reads, checks, and deletes stored files

### Amazon S3 storage

When `file.storage.type=s3`, `S3Storage`:

- Stores sequence content in a configured S3 bucket
- Uses a job-specific object key under `dna-sequence/`
- Stores the target database as object metadata
- Returns the object key as the input reference
- Reads, checks, and deletes stored objects through the AWS SDK for Java

The service layer will depend on `StorageUtility`, allowing storage backends to change without changing job orchestration logic.

## Technology

- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA and Hibernate
- PostgreSQL
- H2 for persistence tests
- Flyway
- AWS SDK for Java
- Maven Wrapper
- JUnit 5 and Mockito
- GitHub Actions

## Project structure

```text
src/
├── main/
│   ├── java/com/yodishtr/alignment_service/
│   │   ├── config/            Configuration properties and S3 client setup
│   │   ├── dto/               API request and response objects
│   │   ├── entity/            JPA entities and value objects
│   │   ├── exceptionhandler/  Consistent exception responses
│   │   ├── mapper/            DTO and entity transformations
│   │   ├── repository/        Spring Data JPA repositories
│   │   ├── service/           Job orchestration layer under development
│   │   └── storage/           Local and S3 storage implementations
│   └── resources/
│       └── application.properties.template
└── test/
    ├── java/                  Unit and persistence tests
    └── resources/             Test configuration
```

## Requirements

- JDK 21
- PostgreSQL
- An Amazon S3 bucket and AWS credentials only when using S3 storage

The Maven Wrapper is included, so a separate Maven installation is not required.

## Local setup

### 1. Clone the repository

```bash
git clone https://github.com/Yodishtr/Sequence-Alignment-SaaS-Platform.git
cd Sequence-Alignment-SaaS-Platform
```

### 2. Create the PostgreSQL database

Create a local database:

```sql
CREATE DATABASE sequence_alignment_service;
```

Database migrations are still being developed. The complete application startup path may therefore change while the persistence schema is finalized.

### 3. Create the application configuration

Create:

```text
src/main/resources/application.properties
```

Do not commit this file. It is already excluded by `.gitignore` because it may contain credentials.

For local filesystem storage, use configuration similar to:

```properties
spring.application.name=alignment-service

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/sequence_alignment_service
spring.datasource.username=YOUR_DATABASE_USERNAME
spring.datasource.password=YOUR_DATABASE_PASSWORD

spring.flyway.enabled=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.open-in-view=false

server.port=8080

file.storage.type=local
file.storage.data.local.root-directory=/absolute/path/to/alignment-data
```

The storage directory is created during application startup if it does not already exist.

### 4. Use S3 storage instead

To activate the S3 implementation, replace the local storage properties with:

```properties
file.storage.type=s3

aws.s3.region=ca-central-1
aws.s3.bucket-name=YOUR_BUCKET_NAME
aws.s3.access-key=${AWS_ACCESS_KEY_ID}
aws.s3.secret-key=${AWS_SECRET_ACCESS_KEY}
```

Set the credentials in your shell rather than committing them:

```bash
export AWS_ACCESS_KEY_ID="your-access-key"
export AWS_SECRET_ACCESS_KEY="your-secret-key"
```

For a production application, the preferred approach would be an IAM role or another temporary-credential mechanism rather than long-lived static keys.

## Run the tests

On macOS or Linux:

```bash
./mvnw test
```

On Windows:

```powershell
mvnw.cmd test
```

The current test suite covers:

- Entity relationships and repository queries
- DTO getters, setters, and validation-related behavior
- Configuration-property binding
- S3 client construction
- Local storage operations
- S3 storage behavior with mocked dependencies
- Global exception-response handling

## Run the application

After PostgreSQL and the selected storage backend are configured:

```bash
./mvnw spring-boot:run
```

The application is still under active development. There are not yet public REST endpoints for submitting or retrieving alignment jobs.

## Continuous integration

The GitHub Actions workflow runs the Maven verification lifecycle for pushes and pull requests targeting `main`.

```bash
./mvnw verify
```

## Design decisions

### UUID resource identifiers

Externally meaningful records use UUIDs so identifiers are not predictable sequential database numbers and can be generated without relying on a single database sequence.

### Sequence data outside PostgreSQL

The database stores job metadata and an input reference rather than treating potentially large sequence content as an ordinary entity field. This separates structured metadata from file or object storage.

### Storage selected by configuration

`@ConditionalOnProperty` activates either the local or S3 component. Local storage supports development and testing, while S3 represents the cloud-oriented deployment path.

### DTOs separated from entities

API-facing objects are separate from persistence entities. This prevents the database model from becoming the public HTTP contract and allows request validation and response formatting to evolve independently.

### Focused persistence tests

Repository and relationship behavior is tested with H2 through `@DataJpaTest`. This verifies mappings and queries without starting the full application or requiring PostgreSQL for every test run.

## Known limitations

- The job service is currently a skeleton.
- REST controllers have not yet been implemented.
- Alignment tools are not yet invoked by the application.
- Background execution and polling are planned but incomplete.
- Database migrations are not yet included.
- Tenant authentication and quota enforcement are not complete.
- The S3 configuration currently uses explicitly supplied credentials.
- The local file format and overwrite behavior are still being refined.
- The complete service has not yet been deployed.

## Roadmap

1. Complete job creation and persistence orchestration
2. Add tenant and API-key resolution
3. Expose job submission and status endpoints
4. Add Flyway migrations for PostgreSQL
5. Execute BLAST and Smith-Waterman through isolated processes
6. Add asynchronous job scheduling and timeout handling
7. Persist execution metrics and result summaries
8. Add integration and end-to-end tests
9. Add containerized local infrastructure
10. Deploy a documented demonstration environment

## What I am learning

This project is helping me move beyond isolated CRUD features and reason about a service as a collection of boundaries: API contracts, tenant ownership, persistent state, external storage, native processes, failure handling, configuration, and testing. The incomplete portions are deliberately documented so the repository shows both the implemented foundation and the next engineering steps.
