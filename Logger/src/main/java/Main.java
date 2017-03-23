import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by user on 23.03.2017.
 */
public class Main {
    private static final Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
            BasicConfigurator.configure();
            LOG.trace("trace");
            LOG.debug("debug");
            LOG.info("info");
            LOG.warn("warn");
            LOG.error("error");
            LOG.fatal("fatal");
    }
}
