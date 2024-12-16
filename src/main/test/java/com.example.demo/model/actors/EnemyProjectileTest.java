import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemyProjectileTest {
	@Test
	void testUpdatePosition() {
		EnemyProjectile projectile = new EnemyProjectile(500, 300);
		double initialX = projectile.getTranslateX();
		projectile.updatePosition();
		assertTrue(projectile.getTranslateX() < initialX, "Enemy projectile should move left.");
	}
}
