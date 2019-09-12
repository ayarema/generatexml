package example.users;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

@KarateOptions(tags = "@users")
public class UsersRunner {

    private static final Logger LOGGER = LogManager.getLogger(UsersRunner.class);

    @Test
    public void testParallelUser() {
        Results results = Runner.parallel(getClass(), 3);
    }
}
