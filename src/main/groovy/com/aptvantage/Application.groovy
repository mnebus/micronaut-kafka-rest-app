package com.aptvantage

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*
import org.apache.kafka.clients.admin.AdminClient

import javax.inject.Inject

@OpenAPIDefinition(
    info = @Info(
            title = "kafka-rest-app",
            version = "0.0"
    )
)
@CompileStatic
class Application {

    @Inject
    AdminClient adminClient

    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}
