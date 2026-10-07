package dev.ledger.account;

import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(long id);

    long count();
}
