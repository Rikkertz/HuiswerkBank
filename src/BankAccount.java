import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    public final String accountNumber;
    private String owner;
    private double balance;
//    private String[] transactionHistory = new String[100];
    private List<String> transactionHistory;

    // Method to track and log method invocations
    public void logTransaction(String methodName, Object... args) {

        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder logMessage = new StringBuilder(timestamp.format(formatter));
        logMessage.append(" - ").append(methodName);
        if (args.length > 0) {
            logMessage.append(" [");
            for (int i = 0; i < args.length; i++) {
                logMessage.append(args[i]);
                if (i < args.length - 1) {
                    logMessage.append(", ");
                }
            }
            logMessage.append("]");
        }
        transactionHistory.add(logMessage.toString());
    }
    public void logWelcome(String bank) {
        logTransaction("Welcome to " + bank + ". Your account number is: " + accountNumber +'.');
    }

    /**
     *
     * @param accountNumber Unique account number.
     * @param balance Starting cash.
     */
    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        transactionHistory = new ArrayList<>();
    }
    public BankAccount(String owner, String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        transactionHistory = new ArrayList<>();
        logTransaction("Welcome to BankyMcBankface. Your account number is: " + accountNumber +'.');
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        for (String transaction:transactionHistory
             ) {
            if (transaction == null) {
                return history.toString();
            }
            history.append(transaction).append("\n");
        }
        return history.toString();
    }

    /**
     * This method gets the account balance in the default Euros currency.
     *
     * @return The account balance in euros.
     */
    public double getBalance() {
        return balance;
    }
    /**
     * This method converts the account balance from Euro to the given currency.
     *
     * @param currency  Three-letter code of a foreign currency.
     * @return The account balance in the foreign currency.
     */
    public double getBalance(String currency) {

        return balance * Bank.exchangeRates.get(currency);
    }

    public void deposit(double money) {
        if (money < 0) {
            System.out.println("Invalid deposit!");
            return;
        }
        this.balance += money;
        logTransaction("deposit", money);
    }
    public void withdraw(double money) {
        if (this.balance < money) {
            throw new RuntimeException("You do not have enough funds!");
        }
        this.balance -= money;
        logTransaction("withdraw", money);
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=€" + balance +
                "}";
    }

}
