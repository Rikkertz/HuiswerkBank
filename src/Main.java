public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();
        Person piet = new Person("Piet");
        Person guest = Person.guest;

        // Nieuwe rekeningen aanmaken

        bank.createAccount(piet, "NL01ABCD1234567890", 1000.0);
        bank.createAccount("NL02EFGH1234567890", 500.0);
        bank.createAccount("NL03IJKL1234567890", 2000, "Savings");

        // Geld storten
        System.out.println(bank.getAccounts("NL01ABCD1234567890"));
        System.out.println(bank.getAccounts("NL02EFGH1234567890"));
        System.out.println(bank.getAccounts("NL03IJKL1234567890"));
        bank.deposit("NL01ABCD1234567890", 250.0);

        // Geld opnemen
        bank.withdraw("NL03IJKL1234567890", 1000.0);

        // Saldo controleren

        System.out.println(bank.getBalance("NL01ABCD1234567890"));
        System.out.println(bank.getBalance("NL02EFGH1234567890"));

        bank.transfer("NL01ABCD1234567890", "NL02EFGH1234567890", 500);

        System.out.println(bank.getBalance("NL01ABCD1234567890", "GBP"));
        System.out.println(bank.getBalance("NL02EFGH1234567890", "USD"));

        System.out.println(bank.getAccounts("NL01ABCD1234567890"));
        System.out.println(bank.getAccounts("NL02EFGH1234567890"));
        System.out.println(bank.getAccounts("NL03IJKL1234567890"));
        System.out.println(bank.getBalance("NL03IJKL1234567890","COW"));

    }
}