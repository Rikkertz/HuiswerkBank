public class Main {
    public static void main(String[] args) {


        Bank bank = new Bank("BankyMcBankface");

        // Nieuwe rekeningen aanmaken

        bank.createAccount("NL01ABCD1234567890", 1000.0);
//        bank.createAccount("NL01ABCD1234567890", 900.0);

        bank.createAccount("NL02EFGH1234567890", 500.0);
//        bank.createAccount("NL02EFGH1234567890", 500.0);

        // Geld storten

        bank.deposit("NL01ABCD1234567890", 250.0);

        // Geld opnemen
        bank.withdraw("NL02EFGH1234567890", 200.0);

        // Saldo controleren

        System.out.println(bank.getAccount("NL01ABCD1234567890"));
        System.out.println(bank.getAccount("NL02EFGH1234567890"));

        bank.transfer("NL01ABCD1234567890", "NL02EFGH1234567890", 500);

        System.out.println(bank.getAccount("NL01ABCD1234567890").getBalance("JPY"));
        System.out.println(bank.getAccount("NL02EFGH1234567890").getBalance());

        System.out.println(bank);

        System.out.println(Bank.convert("EUR","JPY", bank.getAccount("NL01ABCD1234567890").getBalance()));
        System.out.println(bank.getAccount("NL01ABCD1234567890").getTransactionHistory());
        System.out.println(bank.getAccount("NL02EFGH1234567890").getTransactionHistory());
    }

}