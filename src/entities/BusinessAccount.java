package entities;

import entities.exceptions.DomainException;

import java.util.Scanner;

public final class BusinessAccount extends Account {
    // atributes
    private final Scanner scan = new Scanner(System.in);
    private Double loanLimit;

    // Constructors
    private BusinessAccount() {
        super();
    }

    public BusinessAccount(Integer accountNumber, String holder, Double balance, Double loanLimit) throws DomainException {
        super(accountNumber, holder, balance);
        this.loanLimit = loanLimit;
    }

    // Methods
    public void loan(double amount) throws DomainException {
        if (amount <= loanLimit) {
            balance += amount - 10.0;
        } else {
            throw new DomainException("Invalid! Loan limit is less than the amount\n");
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format("%nLoan limit: %.2f", loanLimit);
    }

    @Override
    public void purchase(double amount) throws DomainException {
        if ((balance + loanLimit) < amount) {
            throw new DomainException("Invalid! Amount cannot be greater than the balance\n");
        }
        if (amount > balance && (balance + loanLimit) >= amount) {
            System.out.println("Invalid! Balance is less than the amount. Do you want to use the loan limit?(y/n)");
            char responseLoan = scan.nextLine().toLowerCase().charAt(0);
            if (responseLoan == 'y') {
                balance += loanLimit;
                loanLimit -= loanLimit;
            } else {
                throw new DomainException("Purchase failed! Insufficient balance");
            }
        }
        balance -= amount;
    }


    @Override
    public void withdraw(double amount) throws DomainException {
        super.withdraw(amount);
        // tax
        balance -= 2.0;
    }

    // Getters and setters
    public Double getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(Double loanLimit) {
        this.loanLimit = loanLimit;
    }
}
