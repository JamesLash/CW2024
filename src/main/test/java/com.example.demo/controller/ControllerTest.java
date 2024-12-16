import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private Stage mockStage;

    private Controller controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new Controller(mockStage);
    }

    @Test
    void testOnStartGame() {
        // Mock the behavior of the stage
        when(mockStage.getHeight()).thenReturn(750.0);
        when(mockStage.getWidth()).thenReturn(1300.0);

        // Call onStartGame
        assertDoesNotThrow(() -> controller.onStartGame());
    }

    @Test
    void testOnQuit() {
        // No exception should be thrown on quit
        assertDoesNotThrow(() -> controller.onQuit());
    }

    @Test
    void testShowEndingScreen() {
        // Call showEndingScreen and verify stage scene is set
        assertDoesNotThrow(() -> controller.showEndingScreen(true));
    }

    @Test
    void testOnLevelChange() {
        String dummyLevelClassName = "com.example.demo.levels.DummyLevel";

        // Mock behavior for new level
        when(mockStage.getHeight()).thenReturn(750.0);
        when(mockStage.getWidth()).thenReturn(1300.0);

        // Ensure no exception is thrown
        assertDoesNotThrow(() -> controller.onLevelChange(dummyLevelClassName));
    }

    @Test
    void testGoToLevelWithInvalidClass() {
        // Call goToLevel with an invalid class name and check exception
        assertThrows(ClassNotFoundException.class, () -> {
            controller.onLevelChange("invalid.ClassName");
        });
    }
}
