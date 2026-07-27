package com.example.finalproject;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * Image_processing class consists to two functions:
 * resize the image drawn on canvas to 28 x 28 pixels
 * Saves the processed image
 */
public class Image_processing {
    /**
     * Function to resize the image
     * @param image - the image for processing , resize to 28 X 28 Pixels
     * @return
     */
    public static Image resize_image(Image image){
        ImageView imageview = new ImageView(image);
        imageview.setFitWidth(28);
        imageview.setFitHeight(28);
        imageview.setPreserveRatio(true);
        // Snapshot the resized image
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.BLACK);
        return imageview.snapshot(parameters, null);
    }

    /**
     * Function to save the processed image to the specified file path
     * @param image - The image to save
     * @param filePath - the file path to save the image
     */
    public static void saveImage(Image image, String filePath) {
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
