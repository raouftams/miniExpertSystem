package app.jade;

import app.Ressources;
import app.rule.RuleBase;
import app.rule.RuleVariable;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import javafx.scene.control.TextArea;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Company3 extends Agent {
    Object[] obj=null;
    TextArea display = new TextArea();
    protected void setup(){
        try{
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);
        }catch (Exception e){
            System.out.println(e);
        }

        addBehaviour(new CyclicBehaviour(this) {
            public void action(){
                ACLMessage msg = receive();
                if (msg != null) {
                    if(msg.getPerformative()==ACLMessage.INFORM){

                        try {
                            System.out.println("printing in AN3: ");
                            obj=(Object[]) msg.getContentObject();
                            String jsonString = "";
                            for (int i = 0; i < obj.length; i++) {
                                jsonString += obj[i];
                            }
                            JSONObject jsonObj = (JSONObject) new JSONParser().parse(jsonString);
                            System.out.println("----------" + jsonString);
                            ACLMessage reply = msg.createReply();

                            //Initializing rulebase
                            RuleBase rb = Ressources.AirGBARuleBase();
                            //affecting values to rulabase variables
                            RuleVariable rvar = (RuleVariable)rb.getVariableList().get("Depart");
                            rvar.setValue(String.valueOf(jsonObj.get("depart")));

                            rvar = (RuleVariable)rb.getVariableList().get("Destination");
                            rvar.setValue(String.valueOf(jsonObj.get("destination")));

                            rvar = (RuleVariable)rb.getVariableList().get("DateR");
                            rvar.setValue(String.valueOf(jsonObj.get("dateR")));

                            rvar = (RuleVariable)rb.getVariableList().get("Date");
                            String date = String.valueOf(jsonObj.get("dateD"));
                            rvar.setValue(date);

                            rvar = (RuleVariable)rb.getVariableList().get("NbBillets");
                            rvar.setValue(String.valueOf(jsonObj.get("nbBillets")));

                            rvar = (RuleVariable)rb.getVariableList().get("NbEnfants");
                            rvar.setValue(String.valueOf(jsonObj.get("nbEnfants")));

                            rvar = (RuleVariable)rb.getVariableList().get("NbAdultes");
                            rvar.setValue(String.valueOf(jsonObj.get("nbAdultes")));

                            rvar = (RuleVariable)rb.getVariableList().get("NbAged");
                            rvar.setValue(String.valueOf(jsonObj.get("nbPerAgee")));

                            rvar = (RuleVariable)rb.getVariableList().get("check");
                            rvar.setValue("checkDepart");

                            rb.forwardChain(display);

                            RuleVariable escaleVar = (RuleVariable)rb.getVariableList().get("Voyage");
                            String escale = escaleVar.value;
                            RuleVariable dateDvar = (RuleVariable)rb.getVariableList().get("Date");
                            String dateD = dateDvar.value;
                            RuleVariable dateRvar = (RuleVariable)rb.getVariableList().get("DateR");
                            String dateR = dateRvar.value;
                            RuleVariable prixVar = (RuleVariable)rb.getVariableList().get("Prix");
                            String prix = prixVar.value;
                            int nbAdulte = Integer.parseInt(((RuleVariable)rb.getVariableList().get("NbAdultes")).value);
                            int nbAgee = Integer.parseInt(((RuleVariable)rb.getVariableList().get("NbAged")).value);
                            int nbEnfant = Integer.parseInt(((RuleVariable)rb.getVariableList().get("NbEnfants")).value);

                            int price = Integer.parseInt(prix);

                            int finalPrice = price*(nbAdulte) + (price-(15*price/100))*(nbEnfant+nbAgee);

                            Offer offre = new Offer("British airline", escale, dateD, dateR, String.valueOf(finalPrice));
                            reply.setContent(offre.toString());
                            myAgent.send(reply);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else if(msg.getPerformative()==ACLMessage.CONFIRM){
                        //we enter here to confirm l'achat des billets
                        System.out.println("AN2 CONFIRMATION");
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