# Playing with Micronaut, Kafka, and REST

This is an example [Micronaut](https://micronaut.io/) microservice application built as a POC/exercise for me to get to know the framework.
It features:
- REST endpoint that accepts a topic and message as input and produces that message on the given topic
- Kafka consumer that listens to all topics on the given cluster
- embedded kafka integration test
- kafka integration test using [Testcontainers](https://www.testcontainers.org/modules/kafka/)
- Openapi docs published at http://localhost:8080/swagger/kafka-rest-app-0.0.yml
- swagger-ui views published at http://localhost:8080/swagger-ui/
- rapidoc views published at http://localhost:8080/rapidoc/
- prometheus metrics published to http://localhost:8080/prometheus
- leverage Micronaut's (jib) ability to build a docker image
- run as a native image with [GraalVM](https://www.graalvm.org/)

---
## Running the application
For development with hot reloading
1. start the required services in the background
```
docker-compose up -d zookeeper kafka-cluster kafka-ui
```
2. run the application 
```
./mvnw mn:run
```

To run the application in a docker container:
1. build/install the local docker image
```
./mvnw clean package -Dpackaging=docker
```
2. Run with `docker-compose`
```
docker-compose up
```
---
## Building and running "native images" with GraalVM and docker
Prerequisite: Install the GraalVM.
The easiest way to do this is with [sdkman](https://sdkman.io/).
```
sdk install java 21.0.0.r11-grl
gu install native-image
```

### Running the native image locally
1. Build the native image
```
./mvnw clean package -Dpackaging=native-image
```

2. Start the docker dependencies
```
docker-compose up -d zookeeper kafka-cluster kafka-ui
```

3. Run the native binary
```
target/kafka-rest-app
```

### Running a native image in a docker container
1. Build the native docker container
```
./mvnw clean package -Dpackaging=docker-native
```

2. Run with docker-compose
```
docker-compose up
```


## External dependencies
- Java 11 (optional -> GraalVM)
- Docker

---
## Micronaut 2.4.2 Documentation

- [User Guide](https://docs.micronaut.io/2.4.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.4.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.4.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

## Feature openapi documentation

- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)

- [https://www.openapis.org](https://www.openapis.org)

## Feature kafka documentation

- [Micronaut Kafka Messaging documentation](https://micronaut-projects.github.io/micronaut-kafka/latest/guide/index.html)

## Feature Micrometer (Prometheus)

- [Micronaut Micrometer documentation](https://micronaut-projects.github.io/micronaut-micrometer/latest/guide/)

