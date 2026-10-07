package dev.ledger.account;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<Long, Account> accounts = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong();

    @PostConstruct
    void onReady() {
        System.out.println("InMemoryAccountRepository ready, instance " + System.identityHashCode(this));
    }

    @PreDestroy
    void onShutdown() {
        System.out.println("InMemoryAccountRepository closing with " + accounts.size() + " accounts");
    }

    @Override
    public Account save(Account account) {
        Account stored = account.id() == null
                ? new Account(nextId.incrementAndGet(), account.name())
                : account;
        accounts.put(stored.id(), stored);
        return stored;
    }

    @Override
    public Optional<Account> findById(long id) {
        return Optional.ofNullable(accounts.get(id));
    }

    @Override
    public long count() {
        return accounts.size();
    }
}
