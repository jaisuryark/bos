package com.tg.bos.repository;


import com.tg.bos.entities.Account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	 Optional<Account> findByAccountNumber(Long accountNumber);    
}