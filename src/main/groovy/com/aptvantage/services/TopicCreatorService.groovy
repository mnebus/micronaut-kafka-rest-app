package com.aptvantage.services

import org.apache.kafka.clients.admin.AdminClient
import org.apache.kafka.clients.admin.CreateTopicsResult
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.common.errors.TopicExistsException
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.inject.Singleton
import java.util.concurrent.ExecutionException

@Singleton
class TopicCreatorService {

    private static final Logger logger = LoggerFactory.getLogger(TopicCreatorService)

    private AdminClient adminClient

    TopicCreatorService(AdminClient adminClient) {
        adminClient.listTopics()
        this.adminClient = adminClient
    }

    void createTopics(Collection<Topic> topics) {
        topics.forEach(topic -> {
            try {
                createTopic(topic)
            } catch (TopicAlreadyExistsException e) {
                logger.info("skipping creation of topic [${topic.name}] because it already exists.")
            }
        })
    }

    void createTopic(Topic topic) throws TopicAlreadyExistsException {
        //create new topic
        CreateTopicsResult createTopicsResult = adminClient.createTopics(List.of(new NewTopic(topic.name, topic.partitions, (short) 1)))
        try {
            createTopicsResult.all().get()
        } catch (ExecutionException e) {
            if (e.getCause() instanceof TopicExistsException) {
                throw new TopicAlreadyExistsException(topic)
            }
            throw e
        }
        logger.info("Topic ${topic.name} created successfully.")
    }

    static class TopicAlreadyExistsException extends Exception {
        Topic topic

        TopicAlreadyExistsException(Topic topic) {
            this.topic = topic
        }
    }
}
