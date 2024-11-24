package com.tg.bos.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.bos.entities.Account;
import com.tg.bos.entities.Transfer;
import com.tg.bos.entities.TransferMode;
import com.tg.bos.repository.AccountRepository;
import com.tg.bos.repository.TransferRepository;

@Service
public class AccountManager {

	@Autowired
    private AccountRepository accountRepository;
	
	@Autowired
    private TransferRepository transferRepository;

//    @Autowired
//    public AccountManager(AccountRepository accountRepository, TransferRepository transferRepository) {
//        this.accountRepository = accountRepository;
//        this.transferRepository = transferRepository;
//    }

    /**
     * Creates an account using the AccountFactory.
     *
     * @param accountType the type of account to be created.
     * @return the newly created account.
     */
    public Account createAccount(String accountType) {
        return AccountFactory.createAccount(accountType);
    }

    /**
     * Saves the account to the repository.
     *
     * @param account the account to be saved.
     * @return the saved account.
     */
    public Account saveAccount(Account account) {
        // Validate account before saving
        AccountValidator.validateAccount(account);
        return accountRepository.save(account);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param accountId the ID of the account.
     * @return the account with the specified ID.
     */
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    /**
     * Deletes an account by its ID.
     *
     * @param accountId the ID of the account to delete.
     */
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    /**
     * Transfers funds between two accounts.
     *
     * @param fromAccountNumber the account number from which funds are transferred.
     * @param toAccountNumber   the account number to which funds are transferred.
     * @param amount            the amount to be transferred.
     * @param transferMode      the mode of transfer (e.g., IMPS, RTGS).
     * @return the Transfer object representing the transaction.
     */
    public Transfer transferFunds(Long fromAccountNumber, Long toAccountNumber, double amount, TransferMode transferMode) {
        // Fetch and validate accounts
        Account sender = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found."));
        Account receiver = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Receiver account not found."));

        // Use validation service for validations
        AccountValidator.validateAccountIsActive(sender);
        AccountValidator.validateAccountIsActive(receiver);
        AccountValidator.validateTransferAmount(sender, amount);

        // Perform the transfer
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        // Save updated balances
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // Create and save transfer object
        Transfer transfer = new Transfer();
        return transferRepository.save(transfer);
    }


    /**
     * Withdraw funds from an account.
     *
     * @param accountId the account ID.
     * @param pin the PIN of the account holder.
     * @param amount the withdrawal amount.
     * @return the updated account after withdrawal.
     */
    public Account withdrawFunds(Long accountId, int pin, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Use validation from validation service
        AccountValidator.validateWithdrawal(account, pin, amount);

        // Update balance after withdrawal
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    /**
     * Deposit funds into an account.
     *
     * @param accountId the account ID.
     * @param pin the PIN of the account holder.
     * @param amount the deposit amount.
     * @return the updated account after deposit.
     */
    public Account depositFunds(Long accountId, int pin, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // Use validation from validation service
        AccountValidator.validateDeposit(account, pin, amount);

        // Update balance after deposit
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }
}
