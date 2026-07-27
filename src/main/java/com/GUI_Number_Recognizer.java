package com.example.finalproject;
/**
 ========================================================================================================================
 * Author = Anvitha Hiriadka
 * Submitted on 04/27/2024
 * Handwritten Number Recognizer Application --> This Application lets the user draw the number on panel/canvas between
 (0-9), recognizes and predicts the number drawn on the canvas using trained Tensor flow based number recognizer Model.
 ========================================================================================================================
 * This GUI_Number_Recognizer.java file contains GUI Javafx code to allow user to draw numbers (0-9) using touch screen or mouse.
 * Event_handler_canvas.java is the class that contains functions to handle the events such strokes on the canvas by mouse
 and button actions
 * The singleton design pattern is implemented by obtaining the singleton instance of Event_handler_canvas using the getInstance method.
 - a private static instance variable to the Event_handler_canvas class as part of singleton design pattern is created.
 - a private constructor to prevent external instantiation is created - as part of singleton design pattern
 - Static method to access the singleton instance
 * Inheritance, encapsulation and interfaces concepts are used in this project in the following ways:
 * Inheritance concept is covered by the GUI_Number_Recognizer.java class which inherits Application class
 * The implementation of encapsulation is done in  Event_handler_canvas class
 by encapsulating the functionality for handling events on a canvas
 * Implemented CanvasEventHandler as interface to provide a unified interface for handling mouse and keyboard events
 * NOTE: Please ensure that the numbers are drawn accurately. The model achieves a high accuracy rate of 98.94% in predicting handwritten numbers.
 However, in some cases, such as the digits 5 and 8, if they are not recognized initially, a retry with proper drawing may resolve the issue, as it succeeds 95% of the time."
 */
// Importing necessary JavaFX libraries
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;

// Main class for the Handwritten Number Recognizer application
public class GUI_Number_Recognizer extends Application {

    // Start method to initialize the JavaFX application
    public void start(Stage stage) throws IOException {
        //Setting GUI label/Title, font details
        Label label_top = new Label("Digit Writing Panel");
        label_top.setFont(Font.font("Lucida Calligraphy", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 30));

        //Instantiating the Border pane layout class --> Base layout
        BorderPane borderpane = new BorderPane();
        borderpane.setTop(label_top);
        BorderPane.setAlignment(label_top, Pos.CENTER);

        //Creating a canvas object of JavaFX for drawing the handwritten numbers
        Canvas canvas = new Canvas(900,760);

        //Getting the singleton instance of Event_handler_canvas
        //Singleton design pattern used here
        Event_handler_canvas event_handler = Event_handler_canvas.getInstance(canvas);

        //Setting margin for the canvas to be placed center in the border pane
        borderpane.setMargin(canvas, new Insets(50));

        //Creating a pane to wrap canvas for making the canvas-UI creative
        Pane canvaspane = new Pane(canvas);
        canvaspane.setStyle("-fx-background-color: linear-gradient(to bottom right, #66CCFF, #FFFFFF);");
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);
        canvaspane.setEffect(dropShadow);

        //Adding Clear and Predict button
        Button clear_button = new Button("Clear");
        Button predict_button = new Button("PREDICT");

        //Adding output field for the predicted output from the Model
        TextField output_field = new TextField();

        //Setting preferred width and height for the output field
        output_field.setPrefWidth(300); // Set preferred width to 300 pixels
        output_field.setPrefHeight(50);
        output_field.setEditable(false);

        //Setting action/event handling for clear button [to clear the canvas]
        clear_button.setOnAction(e -> {event_handler.clearCanvas();
            output_field.setText("");});

        //Setting action/event handling for predict button
        predict_button.setOnAction(e -> output_field.setText("Predicted Result : " + event_handler.predict_key_action()));

        //Adjusting Size of button
        clear_button.setPrefSize(100,50);
        predict_button.setPrefSize(100,50);
        //Applying css styling for clear button font
        clear_button.setStyle("-fx-font-size: 16px;");
        predict_button.setStyle("-fx-font-size: 16px;");

        //Spacing between buttons
        HBox buttonBox = new HBox(100);
        buttonBox.getChildren().addAll(clear_button, predict_button, output_field);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: #87CEEB;");
        buttonBox.setPrefHeight(100);
        borderpane.setBottom(buttonBox);
        borderpane.setCenter(canvaspane);

        //Setting the Scene and Stage
        Scene scene = new Scene(borderpane, 900,900);
        stage.setTitle("Handwritten Digit Recognizer");
        stage.setScene(scene);
        stage.show();
    }
    // Main method to launch the application
    public static void main(String[] args){
        launch(args);
    }
}