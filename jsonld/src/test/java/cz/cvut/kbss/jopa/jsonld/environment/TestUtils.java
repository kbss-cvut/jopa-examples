package cz.cvut.kbss.jopa.jsonld.environment;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class TestUtils {

    /**
     * Executes the specified procedure in a transaction managed by the specified transaction manager.
     *
     * @param txManager Transaction manager
     * @param procedure Code to execute
     */
    public static void executeInTransaction(PlatformTransactionManager txManager, Runnable procedure) {
        new TransactionTemplate(txManager).execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                procedure.run();
            }
        });
    }
}
