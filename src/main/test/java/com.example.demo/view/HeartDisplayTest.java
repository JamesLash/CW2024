import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HeartDisplayTest {

	private HeartDisplay heartDisplay;

	@BeforeEach
	void setUp() {
		heartDisplay = new HeartDisplay(0, 0, 3);
	}

	@Test
	void testInitialization() {
		assertEquals(3, heartDisplay.getContainer().getChildren().size(), "Heart display should have 3 hearts.");
	}

	@Test
	void testRemoveHeart() {
		heartDisplay.removeHeart();
		assertEquals(2, heartDisplay.getContainer().getChildren().size(), "One heart should be removed.");
	}
}
