package com.agent7799.springboot.security.firstsecurityapp.services;

import com.agent7799.springboot.security.firstsecurityapp.model.Message;
import com.agent7799.springboot.security.firstsecurityapp.repositories.MessageRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public List<Message> loadMessageByUsername(String username) throws NotFoundException {
        Optional<List<Message>> messages = Optional.ofNullable(messageRepository.findAllByUsername(username));
        if (messages.isEmpty()){
            throw  new NotFoundException("Message Not Found!");
        }else{

            return messages.get();
        }
    }

    @Transactional
    public void saveMessage(Message message){
        messageRepository.save(message);
    }

}
