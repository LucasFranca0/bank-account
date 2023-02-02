package entities;

import entities.exceptions.DomainException;

public abstract class Account {

    // Atributes
    private Integer accountNumber;
    private String holder;
    protected Double balance;
    // Constructors
    public Account() {
    }

    public Account(Integer accountNumber, String holder, Double balance) throws DomainException {
        if (balance <= 0) {
            throw new DomainException("Invalid balance");
        }
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = balance;
    }

    // Methods
    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws DomainException {
        if (amount > balance) {
            throw new DomainException("Invalid! Amount to be withdrawn must not be greater than the balance\n");
        }
        balance -= amount;
    }

    public String toString() {
        return "Account Number: " + accountNumber + "\n" +
                "Account Holder: " + holder + "\n" +
                "Balance: $ " + String.format("%.2f", balance);
    }

    public abstract void purchase(double amount) throws DomainException;

    // Getters and setters
    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Double getBalance() {
        return balance;
    }
}
