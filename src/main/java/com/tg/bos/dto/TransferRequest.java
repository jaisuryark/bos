package com.tg.bos.dto;


import com.tg.bos.entities.Account;
import com.tg.bos.entities.TransferMode;

import lombok.Data;

@Data  // Lombok annotation to generate getters, setters, toString, equals, hashCode
public class TransferRequest {

    private Account fromAccount;
    private Account toAccount;
    private double amount;
    private TransferMode transferMode;
    private Long fromAccountNumber; // Sender's account number
    private Long toAccountNumber;   // Receiver's account number
	public Account getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}
	public Account getToAccount() {
		return toAccount;
	}
	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public TransferMode getTransferMode() {
		return transferMode;
	}
	public void setTransferMode(TransferMode transferMode) {
		this.transferMode = transferMode;
	}
	public Long getFromAccountNumber() {
		return fromAccountNumber;
	}
	public void setFromAccountNumber(Long fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}
	public Long getToAccountNumber() {
		return toAccountNumber;
	}
	public void setToAccountNumber(Long toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

    
    
}
