package com.aptvantage

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.consumer.ConsumerRecord

import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

/**
 * kafka-streams requires at least one listener
 */
@KafkaListener(groupId = "kafka-rest-app2", offsetReset = OffsetReset.EARLIEST)
class KafkaClientConsumer {

    BlockingQueue<Message> messages = new LinkedBlockingDeque<>();

    @Topic(patterns = ".*")
    void receive(ConsumerRecord<String,Message> consumerRecord) {
        println("""
Topic: ${consumerRecord.topic()}
Partition: ${consumerRecord.partition()}
Offset: ${consumerRecord.offset()}
Timestamp: ${consumerRecord.timestamp()}
Object: ${consumerRecord.value()}
""")
        messages.add(consumerRecord.value())
    }
}
