import entities.Account;
import entities.BusinessAccount;
import entities.Product;
import entities.SavingsAccount;
import entities.exceptions.DomainException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scan = new Scanner(System.in);
        double total = 0;

        // Classes
        BusinessAccount businessAccount = null;
        SavingsAccount savingsAccount = null;

        System.out.print("Account number: ");
        int accountNumber = scan.nextInt();
        scan.nextLine();
        System.out.print("Holder: ");
        String holder = scan.nextLine();
        System.out.print("Balance: ");
        double balance = scan.nextDouble();

        System.out.printf("%nStarting a bank account, please enter the type of account (1 or 2):%n1) Savings Account %n2) Business Account%n");
        int typeAccount = scan.nextInt();
        try {
            if (typeAccount == 1) {
                System.out.print("Enter the interest rate: ");
                double interestRate = scan.nextDouble();
                savingsAccount = new SavingsAccount(accountNumber, holder, balance, interestRate);
            }
            if (typeAccount == 2) {
                System.out.print("Enter the loan limit: ");
                double loanLimit = scan.nextDouble();
                businessAccount = new BusinessAccount(accountNumber, holder, balance, loanLimit);
            }
        } catch (DomainException e) {
            throw new RuntimeException(e.getMessage());
        }

        List<Product> products = new ArrayList<>();

        scan.nextLine();
        System.out.print("Enter file path: ");
        String sourceFileStr = scan.nextLine();

        File sourceFile = new File(sourceFileStr);
        String sourceFolderStr = sourceFile.getParent();

        boolean sucess = new File(sourceFolderStr + "\\out").mkdir();
        System.out.println("Directory created sucessfully: " + sucess);

        String targetFileStr = sourceFolderStr + "\\out\\extract.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            String itemCsv = br.readLine();
            while (itemCsv != null) {
                String[] fields = itemCsv.split(", ");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);

                products.add(new Product(name, price, quantity));

                itemCsv = br.readLine();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
                if (businessAccount instanceof BusinessAccount) {
                    bw.write(String.format("Account data: %n%s%n", businessAccount));
                    bw.newLine();
                } else {
                    bw.write(String.format("Account data: %n%s%n", savingsAccount));
                    bw.newLine();
                }
                for (Product item : products) {
                    bw.write("Product's name: " + item.getName() + ", quantity: " + item.getQuantity() + ", unitary value: " + item.getPrice() + ", total: " + String.format("%.2f", item.total()));
                    bw.newLine();
                }
                for (Product item : products) {
                    total += item.total();
                }
                bw.write("Total: " + String.format("%.2f%n%n", total));

                // Efetuando a compra
                if (businessAccount instanceof BusinessAccount) {
                    businessAccount.purchase(total);
                    bw.write("Udpated balance: " + String.format("%.2f", businessAccount.getBalance()));
                } else {
                    savingsAccount.purchase(total);
                    bw.write("Udpated balance: " + String.format("%.2f", savingsAccount.getBalance()));
                }

            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
            catch (DomainException e) {
                System.out.println("Error when making purchase, insufficient balance\n");

            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        scan.close();


    }


}