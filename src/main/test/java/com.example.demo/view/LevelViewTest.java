import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.Group;

class LevelViewTest {

	private LevelView levelView;

	@BeforeEach
	void setUp() {
		Group mockRoot = new Group();
		levelView = new LevelView(mockRoot, 3);
	}

	@Test
	void testShowHeartDisplay() {
		Group mockRoot = new Group();
		levelView = new LevelView(mockRoot, 3);
		levelView.showHeartDisplay();
		assertEquals(1, mockRoot.getChildren().size(), "Heart display should be added to the root.");
	}

	@Test
	void testRemoveHearts() {
		levelView.removeHearts(1);
		assertEquals(1, levelView.heartDisplay.getContainer().getChildren().size(), "Hearts should match remaining count.");
	}
}
