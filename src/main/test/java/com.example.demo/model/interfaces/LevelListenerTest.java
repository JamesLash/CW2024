import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelListenerTest {

    @Test
    void testOnLevelChange() {
        LevelListenerStub levelListener = new LevelListenerStub();
        levelListener.onLevelChange("NewLevelClass");
        assertEquals("NewLevelClass", levelListener.getNewLevelClassName(), "LevelListener should store the new level class name.");
    }

    // Stub implementation for testing
    static class LevelListenerStub implements LevelListener {
        private String newLevelClassName;

        @Override
        public void onLevelChange(String newLevelClassName) {
            this.newLevelClassName = newLevelClassName;
        }

        public String getNewLevelClassName() {
            return newLevelClassName;
        }
    }
}
