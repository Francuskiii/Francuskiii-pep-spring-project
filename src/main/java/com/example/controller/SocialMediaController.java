package com.example.controller;

import com.example.service.*;
import com.example.entity.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }    



    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account newAccount) {
        //uname and pword validation
        if (newAccount.getUsername() == null || newAccount.getUsername().isBlank() || newAccount.getPassword() == null || newAccount.getPassword().length() < 4) {
            return ResponseEntity.status(400).body("Invalid username or password.");
        }

        //check if uname exists
        if (accountService.existsByUsername(newAccount.getUsername())) {
            return ResponseEntity.status(409).body("Username already exists.");
        }

        //create account
        Account addedAcc = accountService.addAccount(newAccount);
        if (addedAcc == null) {
            return ResponseEntity.status(400).body("Client Error.");
        }

        return ResponseEntity.ok(addedAcc);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account acc) {
        Account addedAccount = accountService.checkAccount(acc);

        //401 if login incorrect
        if(addedAccount == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        return ResponseEntity.ok(addedAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message msg) {
        //return 400 if null, unsuccessful or incorrect
        if (msg.getMessageText() == null || msg.getMessageText().isBlank() || msg.getMessageText().length() > 255 || messageService.createMsg(msg) == null) {
            return ResponseEntity.status(400).body("Client Error.");
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getAllMessagesById(@PathVariable("messageId") int messageId) {
        Message messages = messageService.getAllMessagesById(messageId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByUser(@PathVariable("accountId") int accountId) {
        List<Message> messages = messageService.getAllMessagesByUser(accountId);
        return ResponseEntity.ok(messages);
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable int messageId, @RequestBody Map<String, String> requestBody) {
        String newMessageText = requestBody.get("messageText");

        int rowsUpdated = messageService.updateMessageText(messageId, newMessageText);

        //return error if none updated
        if (rowsUpdated == 1) {
            return ResponseEntity.ok(rowsUpdated);
        } else {
            return ResponseEntity.status(400).body("Client Error.");
        }
    }
    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable("messageId") int messageId) {
        int deletedRows = messageService.deleteMessage(messageId);

        //return empty if none deleted
        if (deletedRows > 0) {
            return ResponseEntity.ok(deletedRows);
        } else {
            return ResponseEntity.ok().build();
        }
    }
    
    



}
