package com.aptvantage;

import com.aptvantage.services.Topic;
import com.aptvantage.services.TopicCreatorService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Post;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/")
class ProducerController {

    private static final Logger logger = LoggerFactory.getLogger(ProducerController.class);

    private KafkaClientProducer kafkaClientProducer;
    private TopicCreatorService topicCreatorService;


    ProducerController(KafkaClientProducer kafkaClientProducer, TopicCreatorService topicCreatorService) {
        this.topicCreatorService = topicCreatorService;
        this.kafkaClientProducer = kafkaClientProducer;
    }

    @Post(uri = "/produce/{topic}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    Single<Message> produceMessage(String topic, @Body Message message) {
        kafkaClientProducer.sendMessage(topic, message.getKey(), message);
        return Single.just(message);
    }

    @ApiResponses(value = {
            @ApiResponse(),
            @ApiResponse(
                    description = "Topic already exists",
                    responseCode = "409"
            )}
    )
    @Post(uri = "/create-topic", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    Single<Topic> createTopic(@Body Topic topic) throws TopicCreatorService.TopicAlreadyExistsException {
        this.topicCreatorService.createTopic(topic);
        return Single.just(topic);

    }

    @Error(exception = TopicCreatorService.TopicAlreadyExistsException.class)
    HttpResponse<Topic> topicAlreadyExists(TopicCreatorService.TopicAlreadyExistsException exception) {
        return HttpResponse.status(HttpStatus.CONFLICT).body(exception.getTopic());
    }


}
