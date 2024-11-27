package com.example.demo7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextEditorApp extends Application {

    private TextArea textAreaPreview;
    private TextArea textAreaEditor;
    private String currentFilePath;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        textAreaPreview = new TextArea();
        textAreaPreview.setEditable(false);

        textAreaEditor = new TextArea();
        textAreaEditor.setWrapText(true);

        Button btnOpen = new Button("Otwórz plik");
        Button btnSave = new Button("Zapisz");
        Button btnExit = new Button("Wyjdź");

        btnOpen.setOnAction(event -> openFile(primaryStage));

        btnSave.setOnAction(event -> saveFile());

        btnExit.setOnAction(event -> primaryStage.close());


        BorderPane layout = new BorderPane();
        layout.setTop(btnOpen);
        layout.setLeft(textAreaPreview);
        layout.setCenter(textAreaEditor);
        layout.setBottom(btnSave);
        layout.setRight(btnExit);


        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setTitle("Prosty Edytor Tekstów");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void openFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki tekstowe", "*.txt"));


        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            currentFilePath = selectedFile.getAbsolutePath();
            try {

                String content = new String(Files.readAllBytes(Paths.get(currentFilePath)));
                textAreaPreview.setText(content);
                textAreaEditor.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveFile() {
        if (currentFilePath != null) {
            try {

                String content = textAreaEditor.getText();
                Files.write(Paths.get(currentFilePath), content.getBytes());
                System.out.println("Plik zapisany pomyślnie.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
