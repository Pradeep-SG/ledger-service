package dev.ledger.account;

public record Account(Long id, String name) {

    public Account {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("account name must not be blank");
        }
    }

    static Account unsaved(String name) {
        return new Account(null, name);
    }
}
