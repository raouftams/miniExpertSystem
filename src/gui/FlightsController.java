package gui;

import app.Ressources;
import app.rule.RuleBase;
import app.rule.RuleVariable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class FlightsController extends HomeController implements Initializable {

    public class Vols{
        private StringProperty company;
        private StringProperty dateDepart;
        private StringProperty prix;
        private StringProperty duree;
        private StringProperty escale;

        public Vols(){};

        public Vols(String c, String d, String p, String du, String e){
        this.company = new SimpleStringProperty(c);
        this.dateDepart = new SimpleStringProperty(d);
        this.prix = new SimpleStringProperty(p);
        this.duree = new SimpleStringProperty(du);
        this.escale = new SimpleStringProperty(e);
        }

        public String getCompany() {
            return company.get();
        }

        public StringProperty companyProperty() {
            return company;
        }

        public void setCompany(String company) {
            this.company.set(company);
        }

        public String getDateDepart() {
            return dateDepart.get();
        }

        public StringProperty dateDepartProperty() {
            return dateDepart;
        }

        public void setDateDepart(String dateDepart) {
            this.dateDepart.set(dateDepart);
        }

        public String getPrix() {
            return prix.get();
        }

        public StringProperty prixProperty() {
            return prix;
        }

        public void setPrix(String prix) {
            this.prix.set(prix);
        }

        public String getDuree() {
            return duree.get();
        }

        public StringProperty dureeProperty() {
            return duree;
        }

        public void setDuree(String duree) {
            this.duree.set(duree);
        }

        public String getEscale() {
            return escale.get();
        }

        public StringProperty escaleProperty() {
            return escale;
        }

        public void setEscale(String escale) {
            this.escale.set(escale);
        }
    }

    @FXML
    ChoiceBox<String> departP,aeroport, destinationP = new ChoiceBox<String>();
    Spinner<String> nbBillets, age = new Spinner<String>();
    DatePicker departD, retourD = new DatePicker();
    TableView<Vols> tableRes = new TableView<Vols>();
    TableColumn<Vols, String> Compagnie = new TableColumn<Vols,String>();
    TableColumn<Vols, String> Escales = new TableColumn<Vols,String>();
    TableColumn<Vols, String> Duree = new TableColumn<Vols,String>();
    TableColumn<Vols, String> Date = new TableColumn<Vols,String>();
    TableColumn<Vols, String> Prix = new TableColumn<Vols,String>();
    Button reserver = new Button();


    //Doit contenir tous les resultats a afficher dans la tableveiw
    private ObservableList<Vols> Resultats = FXCollections.observableArrayList();

    private  Vols finalChoice = new Vols();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compagnie.setCellValueFactory(data -> data.getValue().company);
        Escales.setCellValueFactory(data -> data.getValue().escale);
        Date.setCellValueFactory(data -> data.getValue().dateDepart);
        Prix.setCellValueFactory(data -> data.getValue().prix);
        Duree.setCellValueFactory(data -> data.getValue().duree);

        RuleBase airAlg = Ressources.AirAlgerieRuleBase();
        String[] airAlgDepart = ((RuleVariable)airAlg.getVariableList().get("Depart")).getLabels().split("[ ]+");
        this.departP.getItems().addAll(airAlgDepart);
        String[] airAlgDestination = ((RuleVariable)airAlg.getVariableList().get("Destination")).getLabels().split("[ ]+");
        this.destinationP.getItems().addAll(airAlgDestination);
        String[] airAlgAeoroports = ((RuleVariable)airAlg.getVariableList().get("Aeroports")).getLabels().split("[ ]+");
        this.aeroport.getItems().addAll(airAlgAeoroports);

        tableRes.setOnMouseClicked(e ->{
            event();
        });
        reserver.setDisable(true);
        //disable the last button
    }

    public void departSelected(ActionEvent event){
        String ville = this.departP.getValue();
        this.destinationP.getItems().remove(ville);
    }

    // Function send form
    // le resultat sera stoké dans Resultats


    //what to do when a row is selected
    private void event(){
        finalChoice = tableRes.getSelectionModel().getSelectedItems().get(0);
        //disable button if no row selected
        //else enable it
        reserver.setDisable(false);
    }

    // Function Send final choice
    //check if choice is not null/empty

}