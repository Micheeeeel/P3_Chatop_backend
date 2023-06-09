package com.chatop.backend.service;

import com.chatop.backend.model.DAOMessage;
import com.chatop.backend.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public DAOMessage createMessage(DAOMessage message) {
        return messageRepository.save(message);
    }

}
