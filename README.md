# Aurora Cloud BDD | End-to-End Testing 

A dedicated repository for behavior-driven testing (BDD) of the Aurora Cloud Platform microservices. 

This repository is designed to run **production-like E2E scenarios** using Docker images of the main services, and is fully compatible with **Kubernetes + Istio + GitOps** environments. 

## Purpose 
- Independent validation of full microservice flows 
- Sceniaro-driven, business-readabl tests using **Cucumber + Spring Boot**
- Supports **AMQP messaging, REST APIs, and DB persistence**
- Can be run against:
> Local Docker Compose images for development
> Kubernetes cluster (Kind / Dev / Staging)
> Istio service mesh environments


## Repository Structure 
```
├── README.md
├── pom.xml                     # BDD test Maven project
├── src/
│   └── test/
│       ├── java/               # Test runner + step definitions
│       └── resources/
│           └── features/       # Cucumber Feature files
├── docker/                     # Optional scripts for local image startup
└── scripts/
    └── run-e2e.sh              # Convenience script to execute all tests
```

## Prerequisites
- Docker (for local images / Testcontainers)
- Kubernetes (optional, for cluster-level tests)
- Istio (optional, for mesh-level tests)
- Java 17+ (for Spring Boot BDD runner)
- Maven 3.9+

Ensure the **Aurora Cloud Platform Docker iamges** are available: 
```
docker pull nanachi1027/aurora-apigw:latest
docker pull nanachi1027/aurora-notification:latest 
docker pull nanachi1027/aurora-fraud:latest
docker pull nanachi1027/aurora-customer:latest
```
> In CI/CD, images are automatically built from main repository and pushed to the registry. 

## Running Locally (Docker-Based)
Start dependencies (RabbitMQ, PostgreSQL, etc.):
```
docker compose -f docker/docker-compose-dev.yml up -d 
```

Run BDD tests: 
```bash 
./mvnw test 
```

or 

```bash 
scripts/run-e2e.sh
```

This will: 
- Launch Testcontainers for any missing dependencies 
- Start main microservices from Docker images 
- Execute all Cucumber scenarios 


## Running in Kubernetes (Kind / Dev Cluster)
### Ensure Helm-deployed services are running: 

```bash 
helm upgrade --install customer ./helm/customer 
helm upgrade --install notification ./helm/notification
```

### Run BDD tests against the cluster
```bash 
KUBECONFIG=~/.kube/kind-config ./mvnw test
```
> BDD tests automatically detect service endpoints via environment variables or cluster DNS. 

## Running with Istio (Service Mesh)
Inject Istio sidecars into microservices:

```bash 
kubectl label namespace default istio-injection=enabled
kubectl rollout restart deployment customer
kubectl rollout restart deployment notification
```

Run BDD tests as usual
> Tests include retries / Awaitility for potential side-introduced delays. 

## Example Feature 
```gherkin
Feature: Notification Service End-to-End 

  Scenario: Customer registration triggers notification 
    Given a new customer is registered
    When the customer-service publishes a message to internal.exchange 
    Then the notification-service should receive the message 
    And the notification-service should persist a notification record
```


## CI/CD Integration 
CI pipeline can: 
- Pull latest Docker images from main repo
- Run all BDD scenarios
- Report success/failure with logs for tracing 

GitOps flow: 
- After successful BDD run, promote images to staging / production


## BDD Responsibility Statement 
Behavior-Driven Development (BDD) in this project is designed to validate **business behavior correctness**, not infrastructure correctness. 

BDD scenarios assert that, given a defined business context, the system behaves as expected across service boundaries (e.g., REST, messaging, and persistence).

Infrastructure concerns such as Kubernetes configuration, service mesh behavior, networking, or deployment topology are intentially **out of scope** for BDD and must be validated by dedicated platform or deployment-level tests.

A failure in BDD should always a **broken business contract**, not an infrastructure or environment issue. 


## License 
[LICENSE](./LICENSE)