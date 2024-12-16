import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShieldImageTest {

	@Test
	void testShieldImageInitialization() {
		ShieldImage shield = new ShieldImage(500, 300);
		assertEquals(500, shield.getLayoutX(), "Shield X position should match initialization.");
		assertEquals(300, shield.getLayoutY(), "Shield Y position should match initialization.");
	}

	@Test
	void testShowAndHideShield() {
		ShieldImage shield = new ShieldImage(500, 300);
		shield.showShield();
		assertTrue(shield.isVisible(), "Shield should be visible after calling showShield.");

		shield.hideShield();
		assertFalse(shield.isVisible(), "Shield should be hidden after calling hideShield.");
	}
}
