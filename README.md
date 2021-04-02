# Playing with Micronaut, Kafka, and REST

This application was an experiment for me to get to know the Micronaut framework.  
It features:
- REST endpoint that accepts a topic and message as input and produces that messages on the given topic
- Kafka consumer that listens to all topics on the given cluster
- embedded kafka integration test
- Openapi docs published at http://localhost:8080/swagger/kafka-rest-app-0.0.yml
- swagger-ui views published at http://localhost:8080/swagger-ui/
- rapidoc views published at http://localhost:8080/rapidoc/
- prometheus metrics published to http://localhost:8080/prometheus

## Running the application
For development with hot reloading
```
./mvnw mn:run
```
> The views for swagger-ui and rapidoc require `./mvnw clean package`


## External dependencies
- Java 11
- Kafka boostrap-servers on localhost:9092 by default to run (not required to build/test)

## Useful kafka ui
The following docker command will make it available at http://localhost:8088
```
docker run -p 8088:8080 \
-e KAFKA_CLUSTERS_0_NAME=local \
-e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=host.docker.internal:9092 \
-e KAFKA_CLUSTERS_0_ZOOKEEPER=host.docker.internal:2128 \
-d provectuslabs/kafka-ui:latest
```
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

