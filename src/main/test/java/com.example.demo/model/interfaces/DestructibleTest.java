import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DestructibleTest {

	@Test
	void testTakeDamage() {
		DestructibleStub destructible = new DestructibleStub();
		destructible.takeDamage();
		assertTrue(destructible.isDamaged(), "Destructible should be marked as damaged after takeDamage.");
	}

	@Test
	void testDestroy() {
		DestructibleStub destructible = new DestructibleStub();
		destructible.destroy();
		assertTrue(destructible.isDestroyed(), "Destructible should be marked as destroyed after destroy.");
	}

	// Stub implementation for testing
	static class DestructibleStub implements Destructible {
		private boolean damaged = false;
		private boolean destroyed = false;

		@Override
		public void takeDamage() {
			this.damaged = true;
		}

		@Override
		public void destroy() {
			this.destroyed = true;
		}

		public boolean isDamaged() {
			return damaged;
		}

		public boolean isDestroyed() {
			return destroyed;
		}
	}
}
