package com.aptvantage

import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject
import java.util.concurrent.TimeUnit

@MicronautTest(environments = 'kafka')
class IntegrationTestSpec extends Specification {

    @Inject
    ProducerController producerController

    @Inject
    KafkaClientConsumer kafkaClientConsumer


    def setup() {
        // Given no messages exist
        kafkaClientConsumer.messages.clear()
    }

    void 'messages are produced to and consumed from a topic'() {
        given: 'A message'
        Message expectedMessage = new Message(
                key: 'key-value',
                otherStuff: [
                        hello: 'tester'
                ]
        )

        when: 'A message is sent to the controller'
        producerController.produceMessage('integration-test-topic-1', expectedMessage)

        then: 'That message is produced and consumed'
        expectedMessage == kafkaClientConsumer.messages.poll(10, TimeUnit.SECONDS)
    }

}
