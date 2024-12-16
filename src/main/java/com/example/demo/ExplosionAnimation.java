package com.example.demo;

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

public class ExplosionAnimation extends ImageView {

    private static final String EXPLOSION_SPRITE_SHEET = "/com/example/demo/images/explosionNormal.png";
    private static final int FRAME_WIDTH = 128;  // Width of each frame
    private static final int FRAME_HEIGHT = 128; // Height of each frame
    private static final int NUM_FRAMES = 8;     // Number of frames in one row
    private static final int FRAME_DURATION = 100; // Duration for each frame in milliseconds

    private final List<Image> frames;

    // Overloaded constructor for default size
    public ExplosionAnimation(double x, double y) {
        this(x, y, FRAME_WIDTH); // Use the default frame width as size
    }

    // Constructor for custom size
    public ExplosionAnimation(double x, double y, int size) {
        this.frames = extractFrames();
        this.setImage(frames.get(0)); // Set the initial frame
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFitWidth(size);  // Dynamic width
        this.setFitHeight(size); // Dynamic height
        this.setPreserveRatio(true);
    }

    private List<Image> extractFrames() {
        List<Image> frameList = new ArrayList<>();
        Image spriteSheet = new Image(Objects.requireNonNull(getClass().getResource(EXPLOSION_SPRITE_SHEET)).toExternalForm());

        for (int i = 0; i < NUM_FRAMES; i++) {
            // Cropping each frame from the sprite sheet
            Image frame = new WritableImage(spriteSheet.getPixelReader(), i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            frameList.add(frame);
        }
        return frameList;
    }

    public void playAnimation(Group root) {
        Timeline animationTimeline = new Timeline();
        for (int i = 0; i < frames.size(); i++) {
            int frameIndex = i;
            animationTimeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(FRAME_DURATION * i),
                    e -> this.setImage(frames.get(frameIndex))
            ));
        }

        animationTimeline.setCycleCount(1); // Run once
        animationTimeline.setOnFinished(e -> root.getChildren().remove(this)); // Remove explosion after animation
        animationTimeline.play();
    }
}
