package com.example.user_authentication.user_authentication_travyotei.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Sends a message to the specified Kafka topic.
     * @param topic The topic name.
     * @param message The message payload.
     */
    public void sendMessage(String topic, String message) {
        System.out.println(String.format("Producing message to %s: %s", topic, message));
        if(topic.equals(null) || topic.isEmpty()){
            topic="test-events";
        }
        this.kafkaTemplate.send(topic, message);
    }

    /**
     * Serialize an object (e.g. AgencyRequestDTO) to JSON and send to topic.
     */
    public void sendAgencyRegistrationEvent(String topic, Object payload) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(payload);
            sendMessage(topic, json);
        } catch (JsonProcessingException e) {
            // log and optionally rethrow or handle
            System.err.println("Failed to serialize payload for Kafka: " + e.getMessage());
        }
    }

}
