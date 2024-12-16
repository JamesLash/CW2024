import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExplosionAnimationTest {
    @Test
    void testInitializationWithDefaultSize() {
        ExplosionAnimation explosion = new ExplosionAnimation(500, 300);
        assertEquals(500, explosion.getLayoutX(), "Explosion X position should match.");
        assertEquals(300, explosion.getLayoutY(), "Explosion Y position should match.");
    }

    @Test
    void testInitializationWithCustomSize() {
        ExplosionAnimation explosion = new ExplosionAnimation(500, 300, 200);
        assertEquals(200, explosion.getFitWidth(), "Explosion width should match custom size.");
        assertEquals(200, explosion.getFitHeight(), "Explosion height should match custom size.");
    }
}
