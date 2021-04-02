package com.aptvantage

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic

@KafkaClient
interface KafkaClientProducer {

    void sendMessage(@Topic String topic, @KafkaKey String key, Message value)

}
