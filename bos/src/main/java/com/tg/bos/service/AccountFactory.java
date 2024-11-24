package com.tg.bos.service;

import com.tg.bos.entities.Account;
import com.tg.bos.entities.Current;
import com.tg.bos.entities.Savings;

public class AccountFactory  {

    /**
     * Creates an Account based on account type.
     *
     * @param accountType the type of the account (e.g., "SAVINGS", "CURRENT").
     * @return the created Account object.
     */
    public static Account createAccount(String accountType) {
        switch (accountType.toUpperCase()) {
            case "SAVINGS":
                return new Savings(); // You can add constructor logic if needed
            case "CURRENT":
                return new Current();  // Similarly, you can add logic for 'Current' accounts
            default:
                throw new IllegalArgumentException("Invalid account type: " + accountType);
        }
    }
}
