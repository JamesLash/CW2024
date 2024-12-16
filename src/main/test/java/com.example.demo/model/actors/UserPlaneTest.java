import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserPlaneTest {

	private UserPlane userPlane;

	@BeforeEach
	void setUp() {
		userPlane = new UserPlane(3); // Initial health of 3
	}

	@Test
	void testInitialHealth() {
		assertEquals(3, userPlane.getHealth(), "Initial health should match the constructor parameter.");
	}

	@Test
	void testMoveUp() {
		userPlane.moveUp();
		userPlane.updatePosition();
		assertTrue(userPlane.getTranslateY() < 0, "Plane should move up.");
	}

	@Test
	void testMoveDown() {
		userPlane.moveDown();
		userPlane.updatePosition();
		assertTrue(userPlane.getTranslateY() > 0, "Plane should move down.");
	}

	@Test
	void testMoveLeft() {
		userPlane.moveLeft();
		userPlane.updatePosition();
		assertTrue(userPlane.getTranslateX() < 0, "Plane should move left.");
	}

	@Test
	void testMoveRight() {
		userPlane.moveRight();
		userPlane.updatePosition();
		assertTrue(userPlane.getTranslateX() > 0, "Plane should move right.");
	}

	@Test
	void testStopMovement() {
		userPlane.moveUp();
		userPlane.stopVertical();
		userPlane.updatePosition();
		assertEquals(0, userPlane.getTranslateY(), "Vertical movement should stop.");

		userPlane.moveRight();
		userPlane.stopHorizontal();
		userPlane.updatePosition();
		assertEquals(0, userPlane.getTranslateX(), "Horizontal movement should stop.");
	}

	@Test
	void testFireProjectile() {
		ActiveActorDestructible projectile = userPlane.fireProjectile();
		assertNotNull(projectile, "Projectile should be created.");
		assertTrue(projectile instanceof UserProjectile, "Projectile should be an instance of UserProjectile.");
	}

	@Test
	void testIncrementKillCount() {
		assertEquals(0, userPlane.getNumberOfKills(), "Initial kill count should be 0.");
		userPlane.incrementKillCount();
		assertEquals(1, userPlane.getNumberOfKills(), "Kill count should increment by 1.");
	}

	@Test
	void testDestroy() {
		userPlane.destroy();
		assertTrue(userPlane.isDestroyed(), "User plane should be marked as destroyed.");
	}
}
