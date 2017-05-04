import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.naming.LimitExceededException;

/**
 * Created by user on 04.05.2017.
 */
public class MemoryLeakLimit implements TestRule {

    private static final long MB = 1_000_000; //MiB = 1024 * 1024
    private long limit = 0;

    public void limit(int limit) {
        this.limit = limit * MB;
    }

    public Statement apply(Statement base, Description description) {
        return new Statement() {
            public long getMemoryUsed() {
                return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            }

            @Override
            public void evaluate() throws Throwable {
                long begin = getMemoryUsed();
                base.evaluate();
                if (getMemoryUsed() - begin > limit) {
                    throw new LimitExceededException("Memory limit");
                }
            }
        };
    }
}
