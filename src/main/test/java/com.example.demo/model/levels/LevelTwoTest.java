import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LevelTwoTest {

	private LevelTwo levelTwo;

	@BeforeEach
	void setUp() {
		levelTwo = new LevelTwo(600, 800);
	}

	@Test
	void testUserHasReachedKillTarget() {
		assertFalse(levelTwo.userIsDestroyed(), "User should not reach kill target initially.");
		for (int i = 0; i < 5; i++) {
			levelTwo.getUser().incrementKillCount();
		}
		assertTrue(levelTwo.getUser().getNumberOfKills() >= 5, "User should reach kill target after enough kills.");
	}

	@Test
	void testStartBossFight() {
		levelTwo.getUser().incrementKillCount();
		levelTwo.checkIfGameOver();
		assertTrue(levelTwo.userIsDestroyed() || levelTwo.getUser().getNumberOfKills() >= 5, "Boss fight should start after sufficient kills.");
	}

	@Test
	void testCheckIfGameOver() {
		levelTwo.getUser().destroy();
		assertDoesNotThrow(levelTwo::checkIfGameOver, "Checking game over should not throw exceptions.");
	}
}
