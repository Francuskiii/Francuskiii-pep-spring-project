package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.*;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMsg(Message msg) {
        if (messageRepository.countByAccountId(msg.getPostedBy()) == 0) {
            return null;
        }else {
            return messageRepository.save(msg); 
        }
        
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getAllMessagesById(int msgId) {
        return messageRepository.findMsgById(msgId).orElse(null);
    }

    public List<Message> getAllMessagesByUser(int accountId) {
        return messageRepository.findMsgByUser(accountId);
    }

    public int deleteMessage(int messageId) {
        return messageRepository.deleteMessageById(messageId);
    }

    public int updateMessageText(int messageId, String newMessageText) {
        if (newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
            return 0; 
        }

        int rowsUpdated = messageRepository.updateMessageText(messageId, newMessageText);
        return rowsUpdated;
    }

}
