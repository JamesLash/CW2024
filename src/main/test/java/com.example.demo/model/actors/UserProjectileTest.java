import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserProjectileTest {

	private UserProjectile userProjectile;

	@BeforeEach
	void setUp() {
		userProjectile = new UserProjectile(500, 300); // Initial position
	}

	@Test
	void testInitialization() {
		assertEquals(500, userProjectile.getLayoutX(), "Initial X position should match.");
		assertEquals(300, userProjectile.getLayoutY(), "Initial Y position should match.");
	}

	@Test
	void testUpdatePosition() {
		double initialX = userProjectile.getTranslateX();
		userProjectile.updatePosition();
		assertTrue(userProjectile.getTranslateX() > initialX, "Projectile should move right.");
	}

	@Test
	void testTakeDamage() {
		userProjectile.takeDamage();
		assertTrue(userProjectile.isDestroyed(), "Projectile should be destroyed after taking damage.");
	}
}
