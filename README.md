# ledger-service

A small double-entry ledger service: accounts, balanced journal entries, balances.

Built incrementally, one layer per commit. Requires JDK 21.

## Run

```
./mvnw -q compile
```

Then run `dev.ledger.LedgerApplication` from your IDE.

## Design Decisions

Recorded here as each one is made.
