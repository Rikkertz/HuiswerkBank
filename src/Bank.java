import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * HELP
 * <p>
 * On initialization, also adds a hashmap with exchange rates. These should be taken from an api but that is too advanced for me.
 */
public class Bank {
    public static void main(String[] args) {
        Bank bank = new Bank();
        System.out.println(bank);
    }

    private final HashMap<String, BankAccount> registry;
    public static final String BANK_NAME = "BankyMcBankface";

    public static HashMap<String, Currency> exchangeRates;
    private ArrayList<String> bankLog;

    static {
        exchangeRates = new HashMap<>();
        exchangeRates.put("EUR", new Currency(1.0, "€"));
        exchangeRates.put("USD", new Currency(1.23396, "$"));
        exchangeRates.put("AUD", new Currency(1.566015, "A$"));
        exchangeRates.put("CAD", new Currency(1.560132, "C$"));
        exchangeRates.put("CHF", new Currency(1.154727, "CHF"));
        exchangeRates.put("CNY", new Currency(7.827874, "¥"));
        exchangeRates.put("GBP", new Currency(0.882047, "£"));
        exchangeRates.put("JPY", new Currency(132.360679, "¥"));
    }

    public Bank() {
        this.registry = new HashMap<>();
    }

    public void createAccount(Person owner, String accountNumber, double balance) {
        if (this.accountExists(accountNumber)) {
            System.out.println("You tried to create an account with account number: " + accountNumber + '.');
            System.out.println("However, this account already exists.");
            return;
        }
        registry.put(accountNumber, new BankAccount(owner, accountNumber, balance));
        getAccount(accountNumber).logWelcome(BANK_NAME);
    }

    public BankAccount getAccount(String accountNumber) {
        if (this.accountExists(accountNumber)) {
            return this.registry.get(accountNumber);
        }
        throw new RuntimeException("This account does not exist!");
    }

    /**
     * This method gets the account balance in the default Euros currency.
     *
     * @param accountNumber The account from which to request the balance.
     * @return The account balance in euros.
     */
    public String getBalance(String accountNumber) {
        BigDecimal balance = BigDecimal.valueOf(getAccount(accountNumber).getBalance());
        return "€".concat(balance.setScale(2, RoundingMode.HALF_UP).toString());
    }

    /**
     * This method converts the account balance from Euro to the given currency.
     *
     * @param accountNumber The account from which to request the balance.
     * @param currency      Three-letter code for a foreign currency.
     * @return The account balance in the specified currency.
     */
    public String getBalance(String accountNumber, String currency) {
        BigDecimal balance = BigDecimal.valueOf(getAccount(accountNumber).getBalance(currency));
        return exchangeRates.get(currency).getSymbol().concat(balance.setScale(2, RoundingMode.HALF_UP).toString());
    }

    public String getBankLog() {
        StringBuilder bankLog = new StringBuilder();
        for (BankAccount account :
                this.registry.values()) {
            bankLog.append("This is the log for: ").append(account.getAccountNumber()).append("\n");
            bankLog.append(account.getTransactionHistory()).append("\n");
        }
        return bankLog.toString();
    }

    private boolean accountExists(String accountNumber) {
        return registry.get(accountNumber) != null;
    }

    public static double convert(String currencyFrom, String currencyTo, double money) {
        try {
            double exchangeRate = exchangeRates.get(currencyTo).getExchangeRate() / exchangeRates.get(currencyFrom).getExchangeRate();
            return exchangeRate * money;
        } catch (NullPointerException e) {
            System.out.println("Unknown currency.");
            return 0.;
        }
    }

    public void deposit(String accountNumber, double balance) {
        this.getAccount(accountNumber).deposit(balance);
    }

    public void withdraw(String accountNumber, double balance) {
        this.getAccount(accountNumber).withdraw(balance);
    }

    public void transfer(String accountNumberOrigin, String accountNumberDestination, double balance) {
        if (!this.accountExists(accountNumberOrigin)) {
            System.out.println("You are trying to withdraw funds from " + accountNumberOrigin + '.');
            System.out.println("However, this account does not exist.");
            return;
        }
        if (!this.accountExists(accountNumberDestination)) {
            System.out.println("You are trying to transfer funds to " + accountNumberDestination + '.');
            System.out.println("However, this account does not exist.");
            return;
        }
        registry.get(accountNumberOrigin).withdraw(balance);
        registry.get(accountNumberDestination).deposit(balance);
    }

    @Override
    public String toString() {
        return BANK_NAME;
    }


    private class BankAccount {

        public final String accountNumber;
        private double balance;
        //    private String[] transactionHistory = new String[100];
        private final List<String> transactionHistory;

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
            logTransaction("Welcome to " + bank + ". Your account number is: " + accountNumber + '.');
        }

        public BankAccount(String accountNumber, double balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
            transactionHistory = new ArrayList<>();
        }


        public BankAccount(Person owner, String accountNumber, double balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
            transactionHistory = new ArrayList<>();
//        logTransaction("Welcome to " + Bank.BANK_NAME + ". Your account number is: " + accountNumber + '.');
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getTransactionHistory() {
            StringBuilder history = new StringBuilder();
            for (String transaction : transactionHistory
            ) {
                if (transaction == null) {
                    return history.toString();
                }
                history.append(transaction).append("\n");
            }
            return history.toString();
        }


        public double getBalance() {
            return balance;
        }

        public double getBalance(String currency) {
            return balance * exchangeRates.get(currency).getExchangeRate();
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

    static class Currency {
        private double exchangeRate;
        private String symbol;

        public Currency(double exchangeRate, String symbol) {
            this.exchangeRate = exchangeRate;
            this.symbol = symbol;
        }

        public double getExchangeRate() {
            return exchangeRate;
        }

        public String getSymbol() {
            return symbol;
        }
    }
}
