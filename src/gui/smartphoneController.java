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
import javafx.scene.control.TextArea;
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
    @FXML
    private TextArea displayArea, conflictSetArea = new TextArea();

    //variables
    String[] brands = ((RuleVariable)rb.getVariableList().get("Brand")).getLabels().split("[ ]+");
    String[] util = ((RuleVariable)rb.getVariableList().get("Utility")).getLabels().split("[ ]+");
    String[] os = ((RuleVariable)rb.getVariableList().get("OS")).getLabels().split("[ ]+");
    String[] ui = ((RuleVariable)rb.getVariableList().get("UI")).getLabels().split("[ ]+");
    String[] sec = ((RuleVariable)rb.getVariableList().get("SecurityLevel")).getLabels().split("[ ]+");
    String[] range = ((RuleVariable)rb.getVariableList().get("PhoneRange")).getLabels().split("[ ]+");
    String[] budget = ((RuleVariable)rb.getVariableList().get("UserBudget")).getLabels().split("[ ]+");


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

    this.displayArea.setEditable(false);
    this.conflictSetArea.setEditable(false);

    Budget.setOnAction(this::setrange);
    Secu.setOnAction(this::setSecurity);
    Utility.setOnAction(this::setUtility);
    OS.setOnAction(this::setOS);
    UI.setOnAction(this::setbrandui);
    Range.setOnAction(this::setRange);
    Brand.setOnAction(this::setBrand);



    }

    public void setrange(ActionEvent event){
        String budget = Budget.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("UserBudget");
        rvar.setValue(budget);

    }

    public void setOS(ActionEvent event){
        String os = OS.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("OS");
        rvar.setValue(os);
    }
    public void setbrandui(ActionEvent event){
        String ui = UI.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("UI");
        rvar.setValue(ui);
    }
    public void setUtility(ActionEvent event){
        String utility = this.Utility.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("Utility");
        rvar.setValue(utility);
    }

    public void setSecurity(ActionEvent event){
        String secuLevel = this.Secu.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("SecurityLevel");
        rvar.setValue(secuLevel);
    }

    public void setBrand(ActionEvent event){
        String brand = this.Brand.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("Brand");
        rvar.setValue(brand);
    }

    public void setRange(ActionEvent event){
        String range = this.Range.getValue();
        RuleVariable rvar = (RuleVariable)this.rb.getVariableList().get("PhoneRange");
        rvar.setValue(range);
    }


    //action this.ruleBase.forwardchaine();
    public void luncher(){
        this.rb.forwardChain(this.conflictSetArea);
        ArrayList<String> text = this.rb.displayVariables();
        for (String s : text) {
            this.displayArea.appendText(s);
        }
        this.displayArea.appendText("\n");

        //text = this.rb.displayConflictSet(this.rb.getRuleList());
        /*for (String s : text) {
            this.conflictSetArea.appendText(s + "\n");
        }
        this.conflictSetArea.appendText("\n");

         */
    }

    public void resetVariables(ActionEvent event){
        displayArea.appendText(this.rb.reset());
        ArrayList<String> text = this.rb.displayVariables();
        for (String s : text) {
            this.displayArea.appendText(s);
        }
        this.displayArea.appendText("\n");
    }
}

/*
 todo: areamodels.setText(machin)
            * JFXTextArea areamodels
 */
