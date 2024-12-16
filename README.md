# Sky Battle

## Description
Sky Battle is a comprehensive reimagining of the classic arcade game **1942**, enhanced to provide a modernized, immersive, and highly strategic gameplay experience. This project originated from a basic and incomplete implementation and has since transformed into a polished, feature-rich game that showcases intricately designed levels, dynamic enemy encounters, and a fully integrated boss battle. The game represents a synthesis of retro charm and contemporary design sensibilities, offering a sophisticated experience for players of all skill levels.

Emphasizing modularity, scalability, and user experience, Sky Battle serves as a case study in effective software maintenance and extension. The project demonstrates how thoughtful refactoring, combined with innovative enhancements, can reinvigorate a classic framework. Whether it's the nuanced movement mechanics or the visually engaging animations, Sky Battle elevates the classic arcade formula to meet modern expectations.

---

## Table of Contents
1. **Description**
2. **Original Version Overview**
3. **Current Version Overview**
4. **Modifications and Justifications**
    - Gameplay Mechanics
    - Visual Enhancements
    - Modular Game Design
5. **Potential Extensions**
6. **Challenges Encountered and Lessons Learned**
7. **Detailed Code Analysis**
    - Key Classes
8. **Game Design Features**
9. **Future Roadmap**
10. **Acknowledgments**

---

## Original Version Overview

The original version of Sky Battle was a rudimentary attempt to recreate **1942**, characterized by limited functionality, basic design, and a lack of player engagement. Although it provided a skeletal framework for an arcade game, it fell short in delivering a complete or satisfying experience. Below is an analysis of its limitations:

### Features in the Original Version

#### Gameplay Mechanics
- **Player Controls**:
  - The player's plane could only move vertically using the **Up** and **Down** arrow keys.
  - Shooting was enabled through the **Spacebar**, but lacked precision and impact.
- **Enemies**:
  - Enemy planes spawned randomly but exhibited simplistic behavior with no scaling difficulty.
- **Win/Loss Conditions**:
  - The game lacked structured transitions or outcomes. When the player's health was depleted, gameplay abruptly ended without clear feedback or resolution.

#### Code Structure
- **Controller Class**:
  - Combined logic for level transitions and gameplay management, resulting in tightly coupled code.
  - Example Issue: Direct references to `LevelParent` within the `Controller` made adding new levels difficult.
  ```java
  if (currentLevel.equals("LevelOne")) {
      loadLevelOne();
  }
  ```

- **LevelParent Class**:
  - Hardcoded behaviors within a shared base class constrained the game's extensibility and maintainability.
  - Example Issue: Collision detection and enemy spawning logic were mixed into the level’s core update loop.
  ```java
  if (enemy.collidesWith(player)) {
      endGame();
  }
  ```

- **Actors**:
  - Simplistic implementations of **UserPlane** and **EnemyPlane** lacked modularity, making it difficult to introduce new mechanics or refine interactions.

- **Visuals and UI**:
  - The static backgrounds and barebones interface failed to create an immersive experience.
  ```java
  Image background = new Image("background1.jpg");
  ```

- **Explosion Effects**:
  - No animations or visual effects for destruction events diminished the game's feedback loop.

---

## Current Version Overview

The updated version of Sky Battle builds upon the original framework, addressing its deficiencies through thoughtful enhancements and new features. The result is a refined, engaging, and replayable game that successfully merges nostalgic elements with modern gameplay standards.

### New Features

#### Enhanced Gameplay Mechanics
- **Expanded Player Controls**:
  - Introduced horizontal movement using the **Left** and **Right** arrow keys, adding strategic depth to evasion and positioning.

  - Implementation:
  ```java
  if (kc == KeyCode.LEFT) {
      user.moveLeft();
  } else if (kc == KeyCode.RIGHT) {
      user.moveRight();
  }
  ```

- **Level Progression**:
  - Designed two levels with increasing complexity:
    1. **Level One**: Features randomized enemy waves with progressive difficulty to challenge player reflexes and strategy.
    2. **Level Two**: A dramatic boss battle incorporating shield mechanics, projectile attacks, and a dynamically updating health bar.

  - Implementation:
  ```java
  public class LevelOne extends LevelParent {
      // Logic for enemy waves.
  }

  public class LevelTwo extends LevelParent {
      // Boss battle mechanics.
  }
  ```

- **Win/Loss Conditions**:
  - Defined transitions for victory (defeating the boss) and defeat (depleting health), supported by animated end screens for resolution and replayability.

#### Visual Enhancements
- **Explosion Effects**:
  - Added sprite-based animations for destruction events, enriching visual feedback and enhancing player satisfaction.

  - Implementation:
  ```java
  ExplosionAnimation explosion = new ExplosionAnimation(enemy.getX(), enemy.getY());
  root.getChildren().add(explosion);
  ```

- **Dynamic Backgrounds**:
  - Developed responsive, level-specific backgrounds that scale dynamically to create immersive environments.
  ```java
  String path = getClass().getResource(backgroundName).toString();
  Image dynamicBackground = new Image(path);
  ```

- **Health Indicators**:
  - Implemented a polished, dynamically updating heart display to provide clear visual representation of player health.

#### Modular Game Design
- **Refactored LevelParent Class**:
  - Decoupled gameplay logic for projectiles, enemy behavior, and collision detection, improving maintainability and scalability.
  ```java
  projectiles.forEach(Projectile::updatePosition);
  detectCollisions();
  ```

- **Interfaces**:
  - Introduced `MenuListener` and `LevelListener` interfaces for clean event handling and future extensibility.
  ```java
  public interface LevelListener {
      void onLevelChange(String nextLevel);
  }
  ```

---

## Modifications and Justifications

### Gameplay Mechanics

#### Original Issue: Limited Player Movement
- **Old Code**:
  ```java
  if (kc == KeyCode.UP) user.moveUp();
  if (kc == KeyCode.DOWN) user.moveDown();
  ```

- **Modification**:
  ```java
  if (kc == KeyCode.LEFT) user.moveLeft();
  if (kc == KeyCode.RIGHT) user.moveRight();
  ```

- **Reason**:
  - Enhanced engagement by broadening tactical options and increasing control fluidity.

#### Original Issue: No Level Progression
- **Old Code**:
  ```java
  if (currentLevel.equals("LevelOne")) {
      loadLevelOne();
  }
  ```

- **Modification**:
  ```java
  public class LevelOne extends LevelParent {
      // Encapsulates enemy waves and progression logic.
  }

  public class LevelTwo extends LevelParent {
      // Implements the boss fight with unique mechanics.
  }
  ```

- **Reason**:
  - Created structured progression and increased variety, keeping players engaged across sessions.

---

## Challenges Encountered and Lessons Learned

- **Challenge**: Refactoring tightly coupled code while maintaining existing functionality.
  - **Solution**: Introduced interfaces (`LevelListener`, `MenuListener`) and thoroughly tested components during integration.

- **Challenge**: Enhancing gameplay without overwhelming new players.
  - **Solution**: Gradual difficulty scaling and visual cues to guide player behavior.

---

## Detailed Code Analysis

### Projectile Class
- **Purpose**:
  - Encapsulates shared behaviors for projectiles, including movement, collision detection, and removal upon impact.

- **Key Methods**:
  ```java
  public void updatePosition() {
      x += velocityX;
      y += velocityY;
  }

  public boolean collidesWith(Actor actor) {
      return getBounds().intersects(actor.getBounds());
  }
  ```

### ExplosionAnimation Class
- **Purpose**:
  - Manages sprite-based destruction animations.

- **Key Innovation**:
  - Automatically removes itself from the scene after the animation completes, ensuring memory efficiency.

---

## Game Design Features

- **Player-Centric Design**: Intuitive controls and responsive feedback mechanics ensure engaging gameplay.
- **Dynamic Challenges**: Scaling difficulty with unique enemy and boss mechanics keeps players invested.
- **Immersive Visuals**: Responsive animations and level-specific designs elevate the gaming experience.

---

## Future Roadmap

1. **New Levels**:
   - Add stages with increasingly complex mechanics and diverse challenges.
2. **Story Integration**:
   - Introduce narrative elements to provide context and enhance engagement.
3. **Customizable Ships**:
   - Allow players to choose or upgrade planes, tailoring their abilities to fit individual play styles.

---

## Acknowledgments

- Inspired by the arcade classic **1942**.
- Special thanks to contributors and the open-source community for resources and assets.

