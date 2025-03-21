package com.example.repository;

import java.util.List;

import com.example.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT COUNT(a) FROM Account a WHERE a.accountId = :accountId")
    Integer countByAccountId(@Param("accountId") Integer accountId);

    @Query("SELECT m FROM Message m WHERE m.messageId = :msgId")
    Optional<Message> findMsgById(@Param("msgId") Integer msgId);

    @Query("SELECT m FROM Message m WHERE m.postedBy = :postedby")
    List<Message> findMsgByUser(@Param("postedby") Integer postedby);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    int updateMessageText(@Param("messageId") Integer messageId, @Param("messageText") String messageText);

    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    int deleteMessageById(@Param("messageId") Integer messageId);
}
