package com.aptvantage;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.math.BigDecimal;

@OpenAPIDefinition(
    info = @Info(
            title = "kafka-rest-app",
            version = "0.0"
    )
)
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        int mb = 1024 * 1024;
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        long xmx = memoryBean.getHeapMemoryUsage().getMax() / mb;
        long xms = memoryBean.getHeapMemoryUsage().getInit() / mb;
        logger.info("Initializing application with the following memory settings:");
        logger.info("Initial Memory (xms) : {}mb",xms);
        logger.info("Max Memory (xmx) : {}mb",xmx);
        Micronaut.run(Application.class, args);
    }
}
