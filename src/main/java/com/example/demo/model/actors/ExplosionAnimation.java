package com.example.demo.model.actors;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Handles explosion animations.
 */
public class ExplosionAnimation extends ImageView {

    private static final String EXPLOSION_SPRITE_SHEET = "/com/example/demo/images/effects/explosionNormal.png";
    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 128;
    private static final int NUM_FRAMES = 8;
    private static final int FRAME_DURATION = 100;

    private final List<Image> frames;

    // Initialize explosion animation with default size
    public ExplosionAnimation(double x, double y) {
        this(x, y, FRAME_WIDTH);
    }

    // Initialize explosion animation with custom size
    public ExplosionAnimation(double x, double y, int size) {
        this.frames = extractFrames();
        this.setImage(frames.get(0));
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFitWidth(size);
        this.setFitHeight(size);
        this.setPreserveRatio(true);
    }

    // Extract frames from sprite sheet
    private List<Image> extractFrames() {
        List<Image> frameList = new ArrayList<>();
        Image spriteSheet = new Image(Objects.requireNonNull(getClass().getResource(EXPLOSION_SPRITE_SHEET)).toExternalForm());

        for (int i = 0; i < NUM_FRAMES; i++) {
            Image frame = new WritableImage(spriteSheet.getPixelReader(), i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            frameList.add(frame);
        }
        return frameList;
    }

    // Plays the explosion animation
    public void playAnimation(Group root) {
        Timeline animationTimeline = new Timeline();
        for (int i = 0; i < frames.size(); i++) {
            int frameIndex = i;
            animationTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(FRAME_DURATION * i),
                    e -> this.setImage(frames.get(frameIndex))
            ));
        }

        animationTimeline.setCycleCount(1);
        animationTimeline.setOnFinished(e -> root.getChildren().remove(this));
        animationTimeline.play();
    }
}
