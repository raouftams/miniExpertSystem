package gui;

import app.Ressources;
import app.jade.CentralAgent;
import app.jade.Form;
import jade.Boot;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ContainerController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FlightsController extends Agent implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToHome(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Home");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToVehicles(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Vehicle.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Smartphones");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSmartphones(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Smartphone.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Smartphones");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToFlights(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Flights.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Flights");
        stage.setScene(scene);
        stage.show();
    }

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
    @FXML
    Spinner<Integer> nbAgees= new Spinner<Integer>(), nbAdultes= new Spinner<Integer>(), nbAdolescents= new Spinner<Integer>(), nbEnfants = new Spinner<Integer>();
    @FXML
    DatePicker departD, retourD = new DatePicker();
    @FXML
    TableView<Vols> tableRes = new TableView<Vols>();
    @FXML
    TableColumn<Vols, String> Compagnie = new TableColumn<Vols,String>();
    @FXML
    TableColumn<Vols, String> Escales = new TableColumn<Vols,String>();
    @FXML
    TableColumn<Vols, String> Duree = new TableColumn<Vols,String>();
    @FXML
    TableColumn<Vols, String> Date = new TableColumn<Vols,String>();
    @FXML
    TableColumn<Vols, String> Prix = new TableColumn<Vols,String>();
    @FXML
    Button reserver = new Button();
    @FXML
    public TextArea textLogs = new TextArea();


    //Doit contenir tous les resultats a afficher dans la tableveiw
    private ObservableList<Vols> Resultats = FXCollections.observableArrayList();

    private  Vols finalChoice = new Vols();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Compagnie.setCellValueFactory(data -> data.getValue().company);
        Escales.setCellValueFactory(data -> data.getValue().escale);
        Date.setCellValueFactory(data -> data.getValue().dateDepart);
        Prix.setCellValueFactory(data -> data.getValue().prix);
        Duree.setCellValueFactory(data -> data.getValue().duree);

        // get destinations from ressources
        ArrayList<String> villes = new ArrayList<>();
        villes.addAll(Arrays.asList(Ressources.airAlgVilles));
        villes.addAll(Arrays.asList(Ressources.airFRVilles));
        Set<String> temp = new HashSet<>(villes);
        villes.clear();
        villes.addAll(temp);

        //get airports from ressources
        ArrayList<String> airports = new ArrayList<>();
        airports.addAll(Arrays.asList(Ressources.airAlgAirports));
        airports.addAll(Arrays.asList(Ressources.airFRAirports));
        temp = new HashSet<>(airports);
        airports.clear();
        airports.addAll(temp);

        //Adding data to javaFx fields
        this.departP.getItems().addAll(villes);
        this.destinationP.getItems().addAll(villes);
        this.aeroport.getItems().addAll(airports);

        textLogs.appendText("azul1");

        tableRes.setOnMouseClicked(e ->{
            event();
        });
        reserver.setDisable(true);

    }

    public void departSelected(ActionEvent event){
        String ville = this.departP.getValue();
        //this.destinationP.getItems().remove(ville);
    }

    // Function send form
    // le resultat sera stok?? dans Resultats
    public void sendData(ActionEvent actionEvent) throws IOException, InterruptedException {
        Form formulaire = new Form(this.departP.getValue(),
                this.destinationP.getValue(),
                this.departD.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                this.retourD.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                this.aeroport.getValue(),
                String.valueOf(this.nbAdultes.getValue()),
                String.valueOf(this.nbEnfants.getValue()),
                String.valueOf(this.nbAgees.getValue())
        );


        /*JSONObject obj = new JSONObject();
        obj.put("depart", this.departP.getValue());
        obj.put("destination", this.destinationP.getValue());
        System.out.println(this.departD.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        obj.put("date", this.departD.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        obj.put("nbPlace", String.valueOf(this.nbBillets.getValue()));
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        String args = out.toString();
        System.out.println(args);
         */

    }


    //what to do when a row is selected
    private void event(){
        finalChoice = tableRes.getSelectionModel().getSelectedItems().get(0);
        //disable button if no row selected
        //else enable it
        reserver.setDisable(false);
    }

    public void resetAll(ActionEvent event) {
        this.destinationP.setValue(null);
        this.departP.setValue(null);
        this.departD.setValue(null);
        this.retourD.setValue(null);
        this.aeroport.setValue(null);
        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0);
        this.nbAdolescents.setValueFactory(factory);
        this.nbAdultes.setValueFactory(factory);
        this.nbAgees.setValueFactory(factory);
        this.nbEnfants.setValueFactory(factory);
    }



    //--------------------------- Agent part -------------------------------------------------------------------

    Object[] obj=null;
    TextArea display = new TextArea();

    protected void setup(){
        System.out.println("ok");
        try{
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);
            System.out.println("bien");
        }catch (Exception e){
            System.out.println(e);
            System.out.println("Tnaket");
        }

        addBehaviour(new CyclicBehaviour(this) {
            public void action(){
                ACLMessage msg = receive();
                if (msg != null) {
                    if(msg.getPerformative()==ACLMessage.INFORM){

                        try {
                            System.out.println("printing in GUI : ");
                            obj=(Object[]) msg.getContentObject();
                            String jsonString = "";
                            for (int i = 0; i < obj.length; i++) {
                                jsonString += obj[i];
                            }
                            JSONObject jsonObj = (JSONObject) new JSONParser().parse(jsonString);
                            System.out.println("----------" + jsonString);
                            ACLMessage reply = msg.createReply();
                            reply.setContent("GUI IS RESPONDING TO AGENT_CENTRAL");
                            myAgent.send(reply);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else if(msg.getPerformative()==ACLMessage.CONFIRM){
                        //we enter here to confirm l'achat des billets
                        System.out.println("GUI CONFIRMATION");
                        ACLMessage reply=msg.createReply();
                        reply.setContent("WELCOME ABROAD :D");
                        myAgent.send(reply);
                        //this deletes the agent
                        doDelete();
                    }

                }
                else {
                    block();
                }
                //whathever
            }
        });
    }

}