package dev.ledger.account;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accounts;

    public AccountService(AccountRepository accounts) {
        this.accounts = accounts;
    }

    public Account open(String name) {
        return accounts.save(Account.unsaved(name));
    }

    public Account get(long id) {
        return accounts.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public long count() {
        return accounts.count();
    }
}
