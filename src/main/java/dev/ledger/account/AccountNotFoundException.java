package dev.ledger.account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(long id) {
        super("account " + id + " not found");
    }
}
