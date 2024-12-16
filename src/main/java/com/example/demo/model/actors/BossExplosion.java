package com.example.demo.model.actors;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

/**
 * Handles boss explosion effect.
 */
public class BossExplosion extends ImageView {

    private static final String BOSS_EXPLOSION_IMAGE = "/com/example/demo/images/effects/BossExplosion.png";
    private static final int EXPLOSION_SIZE = 300;

    // Constructor to set up the explosion image and position
    public BossExplosion(double x, double y) {
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(BOSS_EXPLOSION_IMAGE)).toExternalForm()));
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFitWidth(EXPLOSION_SIZE);
        this.setFitHeight(EXPLOSION_SIZE);
        this.setPreserveRatio(true);
    }

    // Plays the explosion animation and removes it after finishing
    public void playAnimation(Group root) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(300), this);
        scaleUp.setToX(2.0);
        scaleUp.setToY(2.0);

        SequentialTransition sequence = new SequentialTransition(scaleUp);
        sequence.setOnFinished(e -> root.getChildren().remove(this));
        sequence.play();
    }
}
