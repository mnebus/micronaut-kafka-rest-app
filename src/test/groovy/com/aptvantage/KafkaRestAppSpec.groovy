package com.aptvantage

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class KafkaRestAppSpec extends Specification {

    @Inject
    EmbeddedApplication<?> application
    void 'test it works'() {
        expect:
        application.running
    }

}
