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
        boolean result = false;
        if (!accounts.containsValue(account)) {
            accounts.put(account.id(), account);
            result = true;
        }
        return result;
    }

    public synchronized boolean update(Account account) {
        Optional<Account> temp = this.getById(account.id());
        accounts.replace(account.id(), account);
        return true;
    }

    public synchronized boolean delete(int id) {
        Optional<Account> temp = this.getById(id);
        accounts.remove(id);
        return true;
    }

    public synchronized Optional<Account> getById(int id) {
        if (!accounts.containsKey(id)) {
            throw new IllegalStateException(String.format("Not found account by id = %s", id));
        }
        return Optional.of(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Account from = this.getById(fromId).get();
        Account to = this.getById(toId).get();
        boolean result = false;
        if (from.amount() >= amount) {
            accounts.put(fromId, new Account(fromId, from.amount() - amount));
            accounts.put(toId, new Account(toId, to.amount() + amount));
            result = true;
        }
        return result;
    }
}