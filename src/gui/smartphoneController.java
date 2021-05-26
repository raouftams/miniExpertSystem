package gui;



import app.Rule;
import app.RuleBase;
import app.RuleVariable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class smartphoneController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private RuleBase rb = new RuleBase("Smartphones");
    @FXML
    private ChoiceBox<String> Brand, Utility, Secu, OS, UI, Range, Budget = new ChoiceBox<String>();

    //variables
    String[] brands = ((RuleVariable)rb.getVariableList().get("Brand")).getLabels().split("[ ]+");
    String[] util = ((RuleVariable)rb.getVariableList().get("Utility")).getLabels().split("[ ]+");
    String[] os = ((RuleVariable)rb.getVariableList().get("OS")).getLabels().split("[ ]+");
    String[] ui = ((RuleVariable)rb.getVariableList().get("UI")).getLabels().split("[ ]+");
    String[] sec = ((RuleVariable)rb.getVariableList().get("Security level")).getLabels().split("[ ]+");
    String[] range = ((RuleVariable)rb.getVariableList().get("Phone range")).getLabels().split("[ ]+");
    String[] budget = ((RuleVariable)rb.getVariableList().get("User budget")).getLabels().split("[ ]+");


    public void switchToVehicles(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Vehicle.fxml")));
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


    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
    Brand.getItems().addAll(brands);
    Utility.getItems().addAll(util);
    OS.getItems().addAll(os);
    UI.getItems().addAll(ui);
    Range.getItems().addAll(range);
    Secu.getItems().addAll(sec);
    Budget.getItems().addAll(budget);

    Budget.setOnAction(this::setrange);
    Secu.setOnAction(this::setos2);
    OS.setOnAction(this::setBrandAsus);
    Utility.setOnAction(this::setBrandAsus);
    OS.setOnAction(this::setBrand);
    UI.setOnAction(this::setbrandui);
    Utility.setOnAction(this::hadiwkhlas);



    }

    public void setrange(ActionEvent event){
        int budget = Integer.parseInt(Budget.getValue());
        if(budget >= 700){
            Range.setValue("flagship");
        }
        if(budget < 300){
            Range.setValue("budget");
        }
        if(budget<700 && budget >= 300){
            Range.setValue("midrange");
        }
    }
    public void setos2(ActionEvent event){
        if(Secu.getValue().equals("medium")){
            OS.setValue("android");
        }
        if(Secu.getValue().equals("high")){
            OS.setValue("ios");
        }
    }
    public void setBrand(ActionEvent event){
        String os = OS.getValue();
        String ui = UI.getValue();
        if (os.equals("ios")){
            Brand.setValue("apple");
            UI.setValue("none");
        }
    }
    public void setbrandui(ActionEvent event){
        String ui = UI.getValue();
        if (ui.equals("none") && !(OS.getValue()==null) &&!OS.getValue().equals("ios")){
            Brand.setValue("google");
            OS.setValue("android");
        }
        if (ui.equals("miui")){
            Brand.setValue("xiaomi");
            OS.setValue("android");
        }
        if(ui.equals("oneui")){
            Brand.setValue("samsung");
            OS.setValue("android");
        }
    }
    public void setBrandAsus(ActionEvent event){
        if( OS.getValue()!=null && OS.getValue().equals("android")){
            if(Utility.getValue()!=null && Utility.getValue().equals("gaming")){
                Brand.setValue("asus");
            }
        }
    }
    public void hadiwkhlas(ActionEvent event){
        if(Utility.getValue().equals("gaming")){
            Secu.setValue("medium");
        }
        if (Utility.getValue().equals("communication")){
            Secu.setValue("medium");
        }
        if(Utility.getValue().equals("business")){
            Secu.setValue("high");
        }
    }

    //action this.ruleBase.forwardchaine();
    public void luncher(){
        this.rb.forwardChain();
    }
}

/*
 todo: areamodels.setText(machin)
            * JFXTextArea areamodels
 */
