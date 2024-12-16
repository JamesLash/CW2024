import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameOverImageTest {

	@Test
	void testGameOverImageInitialization() {
		GameOverImage image = new GameOverImage(500, 300);
		assertEquals(500, image.getLayoutX(), "X position should match initialization.");
		assertEquals(300, image.getLayoutY(), "Y position should match initialization.");
	}
}
