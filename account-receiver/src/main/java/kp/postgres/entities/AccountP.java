package kp.postgres.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import kp.models.Status;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the account in PostgreSQL.
 */
@Entity
public class AccountP {
    /**
     * Default constructor.
     */
    public AccountP() {
        // constructor is empty
    }

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private long number;
    private Status status;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JsonIgnoreProperties("account")
    private List<OwnerP> owners;

    @OneToOne
    @JsonIgnoreProperties("account")
    private MoneyP money;

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
     * Gets the number.
     *
     * @return the number
     */
    public long getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number the number to set
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the created at date-time.
     *
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the created at date-time.
     *
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the owners.
     *
     * @return the owners
     */
    public List<OwnerP> getOwners() {
        return owners;
    }

    /**
     * Sets the owners.
     *
     * @param owners the owners to set
     */
    public void setOwners(List<OwnerP> owners) {
        this.owners = owners;
    }

    /**
     * Gets the money.
     *
     * @return the money
     */
    public MoneyP getMoney() {
        return money;
    }

    /**
     * Sets the money.
     *
     * @param money the money to set
     */
    public void setMoney(MoneyP money) {
        this.money = money;
    }

}
