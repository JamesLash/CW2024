import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActiveActorTest {
	@Test
	void testMoveHorizontally() {
		ActiveActor actor = new ActiveActorStub("testImage.png", 100, 0, 0);
		actor.moveHorizontally(10);
		assertEquals(10, actor.getTranslateX());
	}

	@Test
	void testMoveVertically() {
		ActiveActor actor = new ActiveActorStub("testImage.png", 100, 0, 0);
		actor.moveVertically(20);
		assertEquals(20, actor.getTranslateY());
	}

	// Mock implementation of ActiveActor for testing
	static class ActiveActorStub extends ActiveActor {
		public ActiveActorStub(String imageName, int imageHeight, double initialXPos, double initialYPos) {
			super(imageName, imageHeight, initialXPos, initialYPos);
		}

		@Override
		public void updatePosition() {
			// Stub method
		}
	}
}
