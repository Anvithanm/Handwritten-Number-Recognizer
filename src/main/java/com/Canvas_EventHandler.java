package com.example.finalproject;

import javafx.scene.input.MouseEvent;
/**
 * This interface defines methods for handling mouse and keyboard events on a canvas.
 */
public interface Canvas_EventHandler {
    /**
     * Handles the event when the mouse is pressed on the canvas.
     * @param mouseEvent The MouseEvent representing the mouse press event.
     */
    void handleMousePressed(MouseEvent mouseEvent);
    /**
     * Handles the event when the mouse is dragged on the canvas.
     * @param mouseEvent The MouseEvent representing the mouse drag event.
     */
    void handleMouseDragged(MouseEvent mouseEvent);
    /**
     * Handles the event when a key is released on the canvas.
     * @param event The KeyEvent representing the key release event.
     */
}
