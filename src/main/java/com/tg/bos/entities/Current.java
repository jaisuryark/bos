package com.tg.bos.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a Current account extending the Account class.
 */
@Entity
//@Table(name = "current_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Current extends Account {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "website_url")
    private String websiteURL;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;
}
