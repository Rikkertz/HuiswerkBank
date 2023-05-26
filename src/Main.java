public class Main {
    public static void main(String[] args) {

        Bank bank = new Bank();
        Person guest = Person.guest;

        // Nieuwe rekeningen aanmaken

        bank.createAccount(guest, "NL01ABCD1234567890", 1000.0);
        bank.createAccount(guest, "NL02EFGH1234567890", 500.0);

        // Geld storten

        bank.deposit("NL01ABCD1234567890", 250.0);

        // Geld opnemen
        bank.withdraw("NL02EFGH1234567890", 200.0);

        // Saldo controleren

        System.out.println(bank.getBalance("NL01ABCD1234567890"));
        System.out.println(bank.getBalance("NL02EFGH1234567890"));

        bank.transfer("NL01ABCD1234567890", "NL02EFGH1234567890", 500);

        System.out.println(bank.getBalance("NL01ABCD1234567890", "GBP"));
        System.out.println(bank.getBalance("NL02EFGH1234567890", "USD"));

    }

}