package com.tg.bos.entities;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Represents a Savings account extending the Account class.
 */
@Entity
//@Table(name = "savings_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Savings extends Account {

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "phone_number", nullable = false)
    private String phoneNo;
}
