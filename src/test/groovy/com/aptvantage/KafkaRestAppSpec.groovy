package com.aptvantage

import com.aptvantage.services.Topic
import com.aptvantage.services.TopicCreatorService
import io.micronaut.context.annotation.Replaces
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import org.apache.kafka.clients.admin.AdminClient
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

    @Replaces(TopicCreatorService)
    static class TestTopicServiceCreator extends TopicCreatorService {

        TestTopicServiceCreator(AdminClient adminClient) {
            super(adminClient)
        }

        @Override
        void createTopic(Topic topic) throws TopicAlreadyExistsException {
            //no-op
        }
    }


}
