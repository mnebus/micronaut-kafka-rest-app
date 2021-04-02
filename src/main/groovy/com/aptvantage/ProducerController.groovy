package com.aptvantage

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.reactivex.Single

@Controller('/')
class ProducerController {

    private KafkaClientProducer kafkaClientProducer

    ProducerController(KafkaClientProducer kafkaClientProducer) {
        this.kafkaClientProducer = kafkaClientProducer
    }

    @Post(uri = '/produce/{topic}', consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    Single<Message> produceMessage(String topic, @Body Message message) {
        kafkaClientProducer.sendMessage(topic, message.getKey(), message)
        return Single.just(message)
    }

}
