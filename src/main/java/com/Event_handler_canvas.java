package com.example.finalproject;
/**
 * This Event_handler_canvas.java file contains various functions to handle the events happening the UI
 * The events handled include : MousePressed, MouseDragged, Predict button actions, clear button actions
 * The singleton design pattern, encapsulation and interface concepts are used here.
 * Event_handler_canvas class implements the interface Canvas_EventHandler class
 * predict_number method here runs the number_recognizer_model2.py model file, performs the prediction function
 and return the result
 * This result is then displayed in the output field  of the GUI.
 * Please note that it take 5 seconds to predict the number, so a wait time for 5 seconds is added in the code
 * */
// Importing necessary JavaFX libraries
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.io.*;


/**
 *  Using GraphicsContext of JavaFx to interpret the graphics on canvas
 *  This class encapsulates the functionality for handling events on a canvas.
 * It implements the CanvasEventHandler interface to provide a unified interface
 * for handling mouse and keyboard events.
 */
public class Event_handler_canvas implements Canvas_EventHandler{
    //Creating a private static instance variable to the Event_handler_canvas class as part of singleton design pattern
    private static Event_handler_canvas instance;
    private GraphicsContext context;
    private Canvas canvas;
    /**
     * Constructs an Event_handler_canvas object with the given Canvas.
     * Initializes the GraphicsContext and sets up event handlers.
     * @param canvas The Canvas object to handle events for.
     */
    // Private constructor to prevent external instantiation - as part of singleton design pattern
    private Event_handler_canvas(Canvas canvas){
        this.canvas = canvas;
        context = canvas.getGraphicsContext2D();
        context.setLineWidth(2);
        setEventHandler(canvas);
    }

    // Static method to access the singleton instance
    public static Event_handler_canvas getInstance(Canvas canvas){
        if (instance==null){
            instance = new Event_handler_canvas(canvas);
        }
        return instance;
    }

    /**
     * Sets up event handlers for the Canvas.
     * @param canvas The Canvas object to set event handlers for.
     */
    private void setEventHandler(Canvas canvas){
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
    }

    /**
     * Handles the event when the mouse is pressed on the canvas.
     * @param mouseEvent The MouseEvent representing the mouse press event.
     */
    public void handleMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
        context.setStroke(Color.BLACK);
        context.setLineWidth(15);
        context.beginPath();
        context.moveTo(mouseEvent.getX(), mouseEvent.getY());
        context.stroke();
    }
    /**
     * Handles the event when the mouse is dragged on the canvas.
     * @param mouseEvent The MouseEvent representing the mouse drag event.
     */
    public void handleMouseDragged(javafx.scene.input.MouseEvent mouseEvent) {
        context.lineTo(mouseEvent.getX(), mouseEvent.getY());
        context.setStroke(Color.BLACK);
        context.setLineWidth(35);
        context.stroke();
    }
    /**
     * Getter method to access the GraphicsContext object.
     *
     * @return The GraphicsContext object.
     */
    public GraphicsContext getContext() {
        return context;
    }

    /**
     * Getter method to access the Canvas object.
     *
     * @return The Canvas object.
     */
    public Canvas getCanvas() {
        return canvas;
    }
    /**
     * This method contains action items for the Predict button
     * Takes the snapshot of the canvas, saves the processed image, triggers the predict_number method
     */
    public String predict_key_action (){
        Image_processing processing_image = new Image_processing();
        Image captured_image = canvas.snapshot(new SnapshotParameters(), null);
        Image image_model = processing_image.resize_image(captured_image);
        processing_image.saveImage(image_model, "processed_image.png");

        try {
            // Call predict_number to get the predicted output from the model
            String predictedOutput = predict_number();
            System.out.println("******************Handwritten Number Prediction from the model**********************");
            System.out.println(predictedOutput);
            // Extract predicted digit from the output string
            String predictedDigitPrefix = "Predicted digit: ";
            int index = predictedOutput.indexOf(predictedDigitPrefix);
            if (index != -1) {
                String predictedDigit = predictedOutput.substring(index + predictedDigitPrefix.length());
                return predictedDigit.trim();
            } else {
                // If the output format is unexpected, return null
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //Method to clear the canvas
    public void clearCanvas(){
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    //Function to connect to the model using Interprocess connection and predict the result
    public String predict_number() throws IOException {
        try{
            // Full path to the Python interpreter and script
            String pythonInterpreter = "python";
            //Please change the path here to the absolute path of number_recognizer_model2.h5 after cloning this Final Project repositary
            String pythonScript = "/Users/anvithahiriadka/IdeaProjects/FinalProject/src/main/java/com/example/finalproject/number_recognizer_model2.py";
            // Build process command
            ProcessBuilder processBuilder = new ProcessBuilder(pythonInterpreter, pythonScript);
            processBuilder.redirectErrorStream(true); // Redirect error stream to input stream

            // Start the process
            Process process = processBuilder.start();
            // Introduce a wait time of 5 seconds
            Thread.sleep(5000); // 5000 milliseconds = 5 seconds

            //Reading output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                outputBuilder.append(line).append("\n");
            }
            //Waiting for the python script to finish
            int exitcode = process.waitFor();
            if (exitcode != 0 ){
                System.out.println("Execution Process Not Completed");
            }
            return outputBuilder.toString().trim();
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}