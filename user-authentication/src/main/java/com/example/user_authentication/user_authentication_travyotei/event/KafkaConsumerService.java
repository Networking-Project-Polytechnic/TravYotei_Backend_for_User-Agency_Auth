package com.example.user_authentication.user_authentication_travyotei.event;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.user_authentication.user_authentication_travyotei.dto.UserRequestDTO;
import com.example.user_authentication.user_authentication_travyotei.dto.UserRequestDTOMapper;
import com.example.user_authentication.user_authentication_travyotei.model.Users;
import com.example.user_authentication.user_authentication_travyotei.repository.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final UsersRepository userRepository;
    private final UserRequestDTOMapper userRequestDTOMapper;
    /**
     * Listens for messages on the "test-events" topic.
     * The consumer group ID is defined in application.properties (e.g., my-application-group).
     * @param message The received message payload.
     */
    @KafkaListener(topics = "Agency-status-update", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        // ObjectMapper mapper = new ObjectMapper();
        try {
            // JsonNode node = mapper.readTree(message);
            UserRequestDTO userRequest = objectMapper.readValue(message, UserRequestDTO.class); 
            System.out.println("Agency update received: " + userRequest);
            Users user = userRepository.findByUserName(userRequest.getUserName())
                    .orElseThrow(() -> new RuntimeException("User not found with username: " + userRequest.getUserName()));
    
            user.setStatus(userRequest.getStatus());
            userRepository.save(user);
            
        } catch (Exception e) {
            System.err.println("Error processing event: " + e.getMessage());
        }
    }
}

