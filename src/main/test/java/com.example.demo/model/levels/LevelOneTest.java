import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelOneTest {

	private LevelOne levelOne;

	@BeforeEach
	void setUp() {
		levelOne = new LevelOne(600, 800);
	}

	@Test
	void testUserHasReachedKillTarget() {
		assertFalse(levelOne.userIsDestroyed(), "User should not reach kill target initially.");
		for (int i = 0; i < 2; i++) {
			levelOne.getUser().incrementKillCount();
		}
		assertTrue(levelOne.getUser().getNumberOfKills() >= 2, "User should reach kill target after enough kills.");
	}

	@Test
	void testCheckIfGameOver() {
		levelOne.getUser().destroy();
		assertDoesNotThrow(levelOne::checkIfGameOver, "Checking game over should not throw exceptions.");
	}
}
