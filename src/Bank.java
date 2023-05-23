import java.util.HashMap;

/**
 * HELP
 * <p>
 * On initialization, also adds a hashmap with exchange rates. These should be taken from an api but that is too advanced for me.
 */
public class Bank {
    public static void main(String[] args) {
        Bank bank = new Bank("BankyMcBankface");
        System.out.println(bank);
    }
    private final HashMap<String, BankAccount> registry;
    public String name;
    public static HashMap<String, Double> exchangeRates;

    static {
        exchangeRates = new HashMap<>();
        exchangeRates.put("EUR", 1.);
        exchangeRates.put("USD", 1.23396);
        exchangeRates.put("AUD", 1.566015);
        exchangeRates.put("CAD", 1.560132);
        exchangeRates.put("CHF", 1.154727);
        exchangeRates.put("CNY", 7.827874);
        exchangeRates.put("GBP", 0.882047);
        exchangeRates.put("JPY", 132.360679);
    }

    public Bank(String name) {
        this.name = name;
        this.registry = new HashMap<>();
    }
    public void createAccount(String accountNumber, double balance){
        if(this.accountExists(accountNumber)) {
            System.out.println("You tried to create an account with account number: " + accountNumber + '.');
            System.out.println("However, this account already exists.");
            return;
        }
        registry.put(accountNumber,new BankAccount(accountNumber, balance));
        getAccount(accountNumber).logWelcome(this.toString());
    }

    public BankAccount getAccount(String accountNumber) {
        if (this.accountExists(accountNumber)) {
            return this.registry.get(accountNumber);
        }
        throw new RuntimeException("This account does not exist!");
    }

    private boolean accountExists(String accountNumber) {
        return registry.get(accountNumber) != null;
    }

    public static double convert(String currencyFrom, String currencyTo, double money) {
        try {
            double exchangeRate = exchangeRates.get(currencyTo) / exchangeRates.get(currencyFrom);
            return exchangeRate * money;
        }
        catch (NullPointerException e) {
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
        return this.name;
    }
}
