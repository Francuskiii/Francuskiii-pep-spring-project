package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean existsByUsername(String username) {
        return accountRepository.findByUsername(username).isPresent();
    }

    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account checkAccount(Account acc) {
        return accountRepository.findByUsernameAndPassword(acc.getUsername(), acc.getPassword()).orElse(null);
    }

}
