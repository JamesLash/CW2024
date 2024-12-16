import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FighterPlaneTest {
	@Test
	void testTakeDamage() {
		FighterPlane fighter = new FighterPlaneStub("fighter.png", 100, 0, 0, 3);
		fighter.takeDamage();
		assertEquals(2, fighter.getHealth());
	}

	@Test
	void testHealthAtZero() {
		FighterPlane fighter = new FighterPlaneStub("fighter.png", 100, 0, 0, 1);
		fighter.takeDamage();
		assertEquals(0, fighter.getHealth());
		assertTrue(fighter.isDestroyed());
	}

	// Mock implementation of FighterPlane for testing
	static class FighterPlaneStub extends FighterPlane {
		public FighterPlaneStub(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
			super(imageName, imageHeight, initialXPos, initialYPos, health);
		}

		@Override
		public ActiveActorDestructible fireProjectile() {
			return null; // Stub method
		}
	}
}
