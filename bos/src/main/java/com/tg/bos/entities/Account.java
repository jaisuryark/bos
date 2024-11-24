package com.tg.bos.entities;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String accountNumber;
	
	@Column(name = "name", nullable = false)
	private String name;
	

	@Enumerated
    private Privilege privilege;

    
    @Column(name = "pin_number", nullable = false)
    private int pinNumber;

    
    @Column(name = "balance", nullable = false)
    private double balance;

    
    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "activated_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Date format specification
    private LocalDate activatedDate;

    @Column(name = "closed_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Date format specification
    private LocalDate closedDate;
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDate getActivatedDate() {
		return activatedDate;
	}

	public void setActivatedDate(LocalDate activatedDate) {
		this.activatedDate = activatedDate;
	}

	public LocalDate getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(LocalDate closedDate) {
		this.closedDate = closedDate;
	}

	
	
}
