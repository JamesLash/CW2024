import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WinImageTest {

	@Test
	void testWinImageInitialization() {
		WinImage winImage = new WinImage(500, 300);
		assertEquals(500, winImage.getLayoutX(), "Win image X position should match initialization.");
		assertEquals(300, winImage.getLayoutY(), "Win image Y position should match initialization.");
	}

	@Test
	void testShowWinImage() {
		WinImage winImage = new WinImage(500, 300);
		winImage.showWinImage();
		assertTrue(winImage.isVisible(), "Win image should be visible after calling showWinImage.");
	}
}
