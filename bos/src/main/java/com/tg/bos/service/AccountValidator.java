package com.tg.bos.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;

import com.tg.bos.entities.Account;
import com.tg.bos.exception.AccountInactiveException;
import com.tg.bos.exception.InsufficientBalanceException;
import com.tg.bos.exception.InvalidAccountException;
import com.tg.bos.exception.InvalidPinException;
import com.tg.bos.repository.AccountRepository;

public class AccountValidator {

	@Autowired
    private AccountRepository accountRepository;
	
	
    // Validate account name (only alphabets)
    public static void validateAccountName(String accountName) {
        if (accountName == null || accountName.trim().isEmpty() || !accountName.matches("^[a-zA-Z ]+$")) {
            throw new IllegalArgumentException("Account name must only contain alphabets and cannot be empty.");
        }
    }
    
    // Validate the complete account (before saving)
    public static void validateAccount(Account account) {
        // Validate account name
        validateAccountName(account.getName());

        // Validate PIN
        validatePin(account.getPinNumber());

        // Validate balance
        validateBalance(account.getBalance());

        // Validate date format for activated and closed dates
        if (account.getActivatedDate() != null && !isValidDateFormat(account.getActivatedDate().toString())) {
            throw new IllegalArgumentException("Invalid date format for activated date. Expected format: yyyy-MM-dd.");
        }

        if (account.getClosedDate() != null && !isValidDateFormat(account.getClosedDate().toString())) {
            throw new IllegalArgumentException("Invalid date format for closed date. Expected format: yyyy-MM-dd.");
        }

        // Age validation - the user must be 18 or older to open a savings account
        if (account.getActivatedDate() != null && !isEligibleForAccount(account.getActivatedDate())) {
            throw new IllegalArgumentException("User must be at least 18 years of age to open a savings account.");
        }
    }

    // Validate PIN (must be exactly 4 digits)
    public static void validatePin(int pinNumber) {
        if (pinNumber <= 0 || pinNumber > 9999) {
            throw new IllegalArgumentException("PIN must be exactly 4 digits and a positive number.");
        }
    }

    // Validate balance (balance cannot be negative)
    public static void validateBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative.");
        }
    }

    // Validate date format (should be valid format)
    public static boolean isValidDateFormat(String date) {
        try {
            LocalDate.parse(date);  // Attempt to parse the date, throws DateTimeParseException if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Validate if user is eligible (18 or older)
    public static boolean isEligibleForAccount(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears() >= 18;
    }

    // Validate withdrawal operation
    public static void validateWithdrawal(Account account, int pin, double amount) {
        // Validate account existence and PIN
        if (account == null) {
            throw new InvalidAccountException("Account not found.");
        }
        if (account.getPinNumber() != pin) {
            throw new InvalidPinException("Invalid PIN.");
        }
        // Validate if account is active
        if (!account.isActive()) {
            throw new AccountInactiveException("Account is inactive.");
        }
        // Validate withdrawal amount (cannot be negative)
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        // Validate if the balance is sufficient
        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException("Insufficient balance for the withdrawal.");
        }
    }

    // Validate deposit operation
    public static void validateDeposit(Account account, int pin, double amount) {
        // Validate account existence and PIN
        if (account == null) {
            throw new InvalidAccountException("Account not found.");
        }
        if (account.getPinNumber() != pin) {
            throw new InvalidPinException("Invalid PIN.");
        }
        // Validate if account is active
        if (!account.isActive()) {
            throw new AccountInactiveException("Account is inactive.");
        }
        // Validate deposit amount (must be greater than zero)
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
    }
    
    
    public Account validateAccountExistsAndActive(Long accountNumber) throws Exception {
        Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new Exception("Account with number " + accountNumber + " not found."));
        if (!account.isActive()) {
            throw new Exception("Account with number " + accountNumber + " is inactive.");
        }
        return account;
    }

    
    public static void validateAccountIsActive(Account account) {
        if (!account.isActive()) {
            throw new IllegalArgumentException("Account with number " + account.getAccountNumber() + " is inactive.");
        }
    }

    public static void validateTransferAmount(Account account, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance in the account.");
        }
    }

   
    public void validateSufficientBalance(Account sender, double amount) throws Exception {
        if (sender.getBalance() < amount) {
            throw new Exception("Insufficient balance in sender's account.");
        }
    }
}
