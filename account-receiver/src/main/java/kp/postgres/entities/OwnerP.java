package kp.postgres.entities;

import jakarta.persistence.*;

/**
 * Represents the account owner in PostgreSQL.
 */
@Entity
public class OwnerP {
    /**
     * Default constructor.
     */
    public OwnerP() {
        // constructor is empty
    }

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
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
