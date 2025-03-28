package kp.postgres.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;

/**
 * Represents money in PostgreSQL.
 */
@Entity
public class MoneyP {
    /**
     * Default constructor.
     */
    public MoneyP() {
        // constructor is empty
    }

    @Id
    @GeneratedValue
    private long id;
    private BigDecimal amount;
    private String currency;

    @OneToOne(mappedBy = "money")
    private AccountP account;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
	@SuppressWarnings("unused")
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the currency.
     *
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the currency.
     *
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    public AccountP getAccount() {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param account the account to set
     */
    public void setAccount(AccountP account) {
        this.account = account;
    }

}