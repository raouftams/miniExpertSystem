package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/gui.Vehicles.fxml"));

            primaryStage.setTitle("Vehicles System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            Main.primaryStage = primaryStage;

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}