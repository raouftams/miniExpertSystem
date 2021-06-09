package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class FlightsController extends HomeController implements Initializable {

    public class Vols{
        private StringProperty company;
        private StringProperty dateDepart;
        private StringProperty prix;
        private StringProperty duree;
        private StringProperty escale;

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
    ChoiceBox<String> departP,aeroport, destinationP;
    @FXML
    Spinner<String> nbBillets, age;
    @FXML
    DatePicker departD, retourD;
    @FXML
    TableView<Vols> tableRes;
    @FXML
    TableColumn<Vols, String> Compagnie;
    @FXML
    TableColumn<Vols, String> Escales;
    @FXML
    TableColumn<Vols, String> Duree;
    @FXML
    TableColumn<Vols, String> Date;
    @FXML
    TableColumn<Vols, String> Prix;

    //Doit contenir tous les resultats a afficher dans la tableveiw
    public ObservableList<Vols> Resultats = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compagnie.setCellValueFactory(data -> data.getValue().company);
        Escales.setCellValueFactory(data -> data.getValue().escale);
        Date.setCellValueFactory(data -> data.getValue().dateDepart);
        Prix.setCellValueFactory(data -> data.getValue().prix);
        Duree.setCellValueFactory(data -> data.getValue().duree);
    }
}