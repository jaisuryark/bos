package com.tg.bos.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a money transfer between two accounts.
 */
@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

	@Id
	private long transferId;
	
    @ManyToOne
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_mode", nullable = false)
    private TransferMode transferMode;
}
