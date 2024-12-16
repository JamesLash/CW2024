import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MenuListenerTest {

    @Test
    void testOnStartGame() {
        MenuListenerStub menuListener = new MenuListenerStub();
        menuListener.onStartGame();
        assertTrue(menuListener.isGameStarted(), "Game should be marked as started after onStartGame.");
    }

    @Test
    void testOnQuit() {
        MenuListenerStub menuListener = new MenuListenerStub();
        menuListener.onQuit();
        assertTrue(menuListener.isGameQuit(), "Game should be marked as quit after onQuit.");
    }

    // Stub implementation for testing
    static class MenuListenerStub implements MenuListener {
        private boolean gameStarted = false;
        private boolean gameQuit = false;

        @Override
        public void onStartGame() {
            this.gameStarted = true;
        }

        @Override
        public void onQuit() {
            this.gameQuit = true;
        }

        public boolean isGameStarted() {
            return gameStarted;
        }

        public boolean isGameQuit() {
            return gameQuit;
        }
    }
}
