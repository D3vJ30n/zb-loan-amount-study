package com.example.consumer

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class LoanConsumer {

    @KafkaListener(topics = ["loan-requests"], groupId = "loan-group")
    fun consume(message: String) {
        println("Consumed message: $message")
    }
}
