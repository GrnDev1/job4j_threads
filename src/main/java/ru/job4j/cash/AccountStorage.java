package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        Optional<Account> temp = this.getById(account.id());

        return accounts.replace(account.id(), accounts.get(account.id()), account);
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        Optional<Account> from = this.getById(fromId);
        Optional<Account> to = this.getById(toId);
        int sumFrom = from.get().amount() - amount;
        if (from.isPresent() && to.isPresent() && sumFrom >= 0) {
                update(new Account(fromId, sumFrom));
                update(new Account(toId, to.get().amount() + amount));
                result = true;
        }
        return result;
    }
}