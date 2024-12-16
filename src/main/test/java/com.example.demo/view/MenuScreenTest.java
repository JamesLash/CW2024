import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MenuScreenTest {

    @Test
    void testCreateMenuScene() {
        MenuListener mockListener = mock(MenuListener.class);
        MenuScreen menuScreen = new MenuScreen(mockListener);

        Stage mockStage = mock(Stage.class);
        when(mockStage.getWidth()).thenReturn(800.0);
        when(mockStage.getHeight()).thenReturn(600.0);

        assertNotNull(menuScreen.createMenuScene(mockStage), "Menu scene should be created.");
    }
}
