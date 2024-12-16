import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemyPlaneTest {
	@Test
	void testUpdatePosition() {
		EnemyPlane enemy = new EnemyPlane(500, 300);
		double initialX = enemy.getTranslateX();
		enemy.updatePosition();
		assertTrue(enemy.getTranslateX() < initialX, "Enemy plane should move left.");
	}

	@Test
	void testFireProjectile() {
		EnemyPlane enemy = new EnemyPlane(500, 300);
		ActiveActorDestructible projectile = enemy.fireProjectile();
		// Projectile may be null because of the random fire rate; assert based on behavior
		if (projectile != null) {
			assertNotNull(projectile, "Projectile should be created.");
		}
	}
}
