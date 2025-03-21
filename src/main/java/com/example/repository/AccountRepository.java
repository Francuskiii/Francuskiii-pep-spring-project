package com.example.repository;

import java.util.List;

//import com.example.service.*;

import com.example.entity.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Account;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("FROM Account WHERE username = :username")
    Optional<Account> findByUsername(@Param("username") String username);
    //Optional<Account> findByUsername(String username);

    @Query("FROM Account WHERE username = :username AND password = :password")
    Optional<Account> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    //Optional<Account> findByUsernameAndPassword(String username, String password);
}