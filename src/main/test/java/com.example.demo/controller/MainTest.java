import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainClassProperties() {
        // Ensure constants are correctly set
        assertEquals(1300, Main.SCREEN_WIDTH);
        assertEquals(750, Main.SCREEN_HEIGHT);
        assertEquals("Sky Battle", Main.TITLE);
    }

    @Test
    void testLaunch() {
        // JavaFX launch cannot be directly tested, but no exception should be thrown
        assertDoesNotThrow(() -> {
            String[] args = {};
            Main.main(args);
        });
    }
}
