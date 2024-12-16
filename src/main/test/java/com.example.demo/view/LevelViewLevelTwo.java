import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Group;

class LevelViewLevelTwoTest {

	@Test
	void testShieldManagement() {
		Group mockRoot = new Group();
		LevelViewLevelTwo levelView = new LevelViewLevelTwo(mockRoot, 3);

		levelView.showShield();
		assertTrue(levelView.shieldImage.isVisible(), "Shield should be visible after calling showShield.");

		levelView.hideShield();
		assertFalse(levelView.shieldImage.isVisible(), "Shield should be hidden after calling hideShield.");
	}
}
