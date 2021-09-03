//@author Chanakya Lahiri
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application { //Main class which makes the stage

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));  //loads the graphical user interface file
        primaryStage.setTitle("User - To Do List"); //header of the software
        primaryStage.setScene(new Scene(root, 1200, 200));  //gives frame including height and width of the software
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); //launches the application
    }
}