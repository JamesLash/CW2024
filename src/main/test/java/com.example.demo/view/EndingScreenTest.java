import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class EndingScreenTest {

    @Test
    void testCreateEndingScene() {
        MenuListener mockListener = mock(MenuListener.class);
        EndingScreen endingScreen = new EndingScreen(mockListener);

        Stage mockStage = mock(Stage.class);
        when(mockStage.getWidth()).thenReturn(800.0);
        when(mockStage.getHeight()).thenReturn(600.0);

        assertNotNull(endingScreen.createEndingScene(mockStage, true), "Ending scene should be created.");
    }
}
