package com.tg.bos.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tg.bos.entities.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
