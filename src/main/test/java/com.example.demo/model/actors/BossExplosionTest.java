import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BossExplosionTest {
    @Test
    void testInitialization() {
        BossExplosion explosion = new BossExplosion(500, 300);
        assertEquals(500, explosion.getLayoutX(), "Explosion X position should match.");
        assertEquals(300, explosion.getLayoutY(), "Explosion Y position should match.");
    }
}
