public class Balance {
    int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int newBalance) {
        if (newBalance > balance) balance = newBalance;
    }
}
