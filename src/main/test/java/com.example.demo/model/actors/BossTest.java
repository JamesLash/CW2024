import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BossTest {
	@Test
	void testInitialHealth() {
		Boss boss = new Boss();
		assertEquals(100, boss.getHealth());
	}

	@Test
	void testShieldActivation() {
		Boss boss = new Boss();
		boss.takeDamage(); // Should not decrease health if shielded
		assertEquals(100, boss.getHealth());
	}

	@Test
	void testFireProjectile() {
		Boss boss = new Boss();
		ActiveActorDestructible projectile = boss.fireProjectile();
		assertNotNull(projectile); // Projectile should be created
	}
}
