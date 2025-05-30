package task.ing.helper;

public class AccountHelper {
    public static String generateAccountNumber() {
        long number = (long) (Math.random() * 1_000_000_000_000_000L);
        return String.format("%016d", number);
    }

    public static String generateIban(String accountNumber) {
        String countryCode = "TR";
        String bankCode = "0009";
        String branchCode = "0001";
        return countryCode + bankCode + branchCode + accountNumber;
    }
}
