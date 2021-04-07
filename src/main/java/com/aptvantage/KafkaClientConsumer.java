package com.aptvantage;

import com.aptvantage.services.TopicCreatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.OffsetReset;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Value;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * kafka-streams requires at least one listener
 */
@KafkaListener(groupId = "kafka-rest-app2", offsetReset = OffsetReset.EARLIEST)
@Singleton
public class KafkaClientConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaClientConsumer.class);

    KafkaClientConsumer(@Value("${kafka-rest-app.auto-create-topics}") String topicJsons,
                        TopicCreatorService creator, ObjectMapper objectMapper) {
        logger.info("auto-creating topics: {}", topicJsons);
        try {
            List<com.aptvantage.services.Topic> topics = objectMapper.readValue(topicJsons, new TypeReference<>() {
            });
            for (com.aptvantage.services.Topic topic : topics) {
                try {
                    creator.createTopic(topic);
                } catch (TopicCreatorService.TopicAlreadyExistsException e) {
                    logger.info("skipping creation of topic [{}] because it already exists.", topic.getName());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to create KafkaClientConsumer: " + e.toString(), e);
        }
    }

    BlockingQueue<Message> messages = new LinkedBlockingDeque<>();

    @Topic(patterns = ".*")
    void receive(ConsumerRecord<String, Message> consumerRecord) {
        logger.info("\nTopic: {}\nPartition: {}\nOffset: {}\nTimestamp: {}\nObject: {}",
                consumerRecord.topic(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                consumerRecord.timestamp(),
                consumerRecord.value());
        messages.add(consumerRecord.value());
    }
}
