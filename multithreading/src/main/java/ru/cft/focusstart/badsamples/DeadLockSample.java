package ru.cft.focusstart.badsamples;

public class DeadLockSample {

    public static void main(String[] args) {
        final Account account1 = new Account("Account1", 10000);
        final Account account2 = new Account("Account2", 5000);
        new Thread(() -> account1.transferMoney(account2, 1000)).start();
        new Thread(() -> account2.transferMoney(account1, 2000)).start();
    }
}

class Account {
    private final String name;
    private long amount;

    Account(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }

    synchronized void transferMoney(Account to, long amount) {
        withdraw(amount);
        to.deposit(amount);
    }

    private synchronized void withdraw(long amount) {
        this.amount -= amount;
        System.out.format("%d written off from %s%n", amount, name);
    }

    private synchronized void deposit(long amount) {
        this.amount += amount;
        System.out.format("%d credited to %s%n", amount, name);
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }
}
