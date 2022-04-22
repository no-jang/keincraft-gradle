package keincraft.engine.log.api;

import org.junit.jupiter.api.Test;
import org.tinylog.Logger;

/**
 * Verifies the functionality of the {@link Logger} class without exceptions.
 */
public class TinylogTest {
    /**
     * Verifies that the tinylog @code{Logger} works without throwing exceptions.
     */
    @Test
    public void testLogging() {
        Logger.trace("trace");
        Logger.debug("debug");
        Logger.info("info");
        Logger.warn("warn");
        Logger.error("error");
    }
}
