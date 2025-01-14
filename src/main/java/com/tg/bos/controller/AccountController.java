package com.tg.bos.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tg.bos.dto.TransferRequest;
import com.tg.bos.entities.Account;
import com.tg.bos.entities.Transfer;
import com.tg.bos.service.AccountManager;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountManager accountManager;

    @Autowired
    public AccountController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @PostMapping("/create")
    public Account createAccount(@RequestParam String accountType) {
        return accountManager.createAccount(accountType);
    }

    @PostMapping("/save")
    public Account saveAccount(@RequestBody Account account) {
        return accountManager.saveAccount(account);
    }

    @GetMapping("/{accountId}")
    public Optional<Account> getAccountById(@PathVariable Long accountId) {
        return accountManager.getAccountById(accountId);
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        accountManager.deleteAccount(accountId);
    }

   
    
    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transferFunds(@RequestBody TransferRequest transferRequest) {
        // Validate input
        if (transferRequest.getAmount() <= 0) {
            return ResponseEntity.badRequest().body(null); // Return 400 for invalid amount
        }

        try {
             //Delegate to AccountManager
            Transfer transfer = accountManager.transferFunds(
                    transferRequest.getFromAccountNumber(),
                    transferRequest.getToAccountNumber(),
                    transferRequest.getAmount(),
                    transferRequest.getTransferMode()
            );
            return ResponseEntity.ok(transfer); // Return 200 with transfer details
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Return 400 for validation errors
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 for other errors
        }
    }

    
    
    
}
