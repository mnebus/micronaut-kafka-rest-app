package com.aptvantage

import groovy.transform.CompileStatic
import io.micronaut.runtime.Micronaut
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.apache.kafka.clients.admin.AdminClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Inject
import java.lang.management.ManagementFactory
import java.lang.management.MemoryMXBean

@OpenAPIDefinition(
    info = @Info(
            title = "kafka-rest-app",
            version = "0.0"
    )
)
@CompileStatic
class Application {

    private static final Logger log = LoggerFactory.getLogger(Application)

    @Inject
    AdminClient adminClient

    static void main(String[] args) {

        int mb = 1024 * 1024;
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean()
        BigDecimal xmx = memoryBean.getHeapMemoryUsage().getMax() / mb
        BigDecimal xms = memoryBean.getHeapMemoryUsage().getInit() / mb
        log.info("Initializing application with the following memory settings:")
        log.info("Initial Memory (xms) : ${xms}mb")
        log.info("Max Memory (xmx) : ${xmx}mb")

        Micronaut.run(Application, args)

    }
}
