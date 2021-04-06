package com.aptvantage.services;

import lombok.Getter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.TopicExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Singleton
public class TopicCreatorService {

    private static final Logger logger = LoggerFactory.getLogger(TopicCreatorService.class);

    private AdminClient adminClient;

    TopicCreatorService(AdminClient adminClient) {
        adminClient.listTopics();
        this.adminClient = adminClient;
    }

    public void createTopic(Topic topic) throws TopicAlreadyExistsException {
        //create new topic
        CreateTopicsResult createTopicsResult = adminClient.createTopics(
                List.of(new NewTopic(topic.getName(), topic.getPartitions(), (short) 1)));
        try {
            createTopicsResult.all().get();
        } catch (ExecutionException | InterruptedException e) {
            if (e.getCause() instanceof TopicExistsException) {
                throw new TopicAlreadyExistsException(topic);
            }
            throw new RuntimeException(e.toString(),e);
        }
        logger.info("Topic ${topic.name} created successfully.");
    }

    @Getter
    public static class TopicAlreadyExistsException extends Exception {
        private Topic topic;

        TopicAlreadyExistsException(Topic topic) {
            this.topic = topic;
        }
    }
}
