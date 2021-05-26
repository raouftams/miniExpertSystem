package gui;



import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class VehicleController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSmartphones(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Smartphone.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Vehicles");
        stage.setScene(scene);
        stage.show();
    }

    public void SwitchtoHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }
}


