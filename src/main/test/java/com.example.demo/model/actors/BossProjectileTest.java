import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BossProjectileTest {
	@Test
	void testUpdatePosition() {
		BossProjectile projectile = new BossProjectile(100);
		double initialX = projectile.getTranslateX();
		projectile.updatePosition();
		assertTrue(projectile.getTranslateX() < initialX, "Projectile should move left.");
	}
}
