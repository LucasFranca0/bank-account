package entities;

import entities.exceptions.DomainException;

public final class SavingsAccount extends Account {

    // Atributes
    private Double interestRate;

    // Constructors
    public SavingsAccount() {
        super();
    }

    public SavingsAccount(Integer accountNumber, String holder, Double balance, Double interestRate) throws DomainException {
        super(accountNumber, holder, balance);
        if (interestRate < 0) {
            throw new DomainException("interest rate must be positive");
        }
        this.interestRate = interestRate;
    }

    // Methods
    public void updateBalance() {
        balance += balance * interestRate;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%nInterest Rate: %.2f", interestRate);
    }

    @Override
    public void purchase(double amount) throws DomainException {
        if (amount > balance) {
            throw new DomainException("Purchase failed! Insufficient balance");
        }
        balance -= amount;
    }

    @Override
    public void withdraw(double amount) {
        balance -= amount;
    }

    // Getters and setters
    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
}
