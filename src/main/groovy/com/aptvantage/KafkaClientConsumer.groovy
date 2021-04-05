package com.aptvantage


import com.aptvantage.services.TopicCreatorService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Value
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Singleton
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque

/**
 * kafka-streams requires at least one listener
 */
@KafkaListener(groupId = "kafka-rest-app2", offsetReset = OffsetReset.EARLIEST)
@Singleton
class KafkaClientConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaClientConsumer)

    KafkaClientConsumer(@Value('${kafka-rest-app.auto-create-topics}') String topicJsons, TopicCreatorService creator) {
        ObjectMapper mapper = new ObjectMapper()
        logger.info("auto-creating topics: ${topicJsons}")
        List<com.aptvantage.services.Topic> topics = mapper.readValue(topicJsons,
                new TypeReference<List<com.aptvantage.services.Topic>>() {})
        creator.createTopics(topics)
    }

    BlockingQueue<Message> messages = new LinkedBlockingDeque<>();

    @Topic(patterns = ".*")
    void receive(ConsumerRecord<String, Message> consumerRecord) {
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
