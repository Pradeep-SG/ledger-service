package dev.ledger;

import dev.ledger.account.Account;
import dev.ledger.account.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan
public class LedgerApplication {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(LedgerApplication.class)) {

            System.out.println("Beans in the container:");
            for (String name : context.getBeanDefinitionNames()) {
                System.out.println("  " + name);
            }

            AccountService service = context.getBean(AccountService.class);
            AccountService again = context.getBean(AccountService.class);
            System.out.println("Same AccountService instance on both lookups: " + (service == again));

            Account cash = service.open("Cash");
            Account revenue = service.open("Revenue");
            System.out.println("Opened " + cash + " and " + revenue);
            System.out.println("Fetched " + again.get(cash.id()));

            int threads = 8;
            int opensPerThread = 25_000;
            try (ExecutorService pool = Executors.newFixedThreadPool(threads)) {
                for (int t = 0; t < threads; t++) {
                    pool.submit(() -> {
                        for (int i = 0; i < opensPerThread; i++) {
                            service.open("Load");
                        }
                    });
                }
            }
            long expected = 2 + (long) threads * opensPerThread;
            System.out.println("Accounts after concurrent opens: " + service.count()
                    + " (expected " + expected + ")");
        }
    }
}
