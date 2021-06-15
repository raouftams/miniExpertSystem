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
import org.json.simple.parser.*;

public class Company1 extends Agent {
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
                        //We will make an if here li tdifferenci between when we send the data
                        //Or when we choose the annexe pour le vol
                        try {
                            System.out.println("printing in AN1 : ");
                            obj=(Object[]) msg.getContentObject();
                            String jsonString = "";
                            for (int i = 0; i < obj.length; i++) {
                                jsonString += obj[i];
                            }
                            JSONObject jsonObj = (JSONObject) new JSONParser().parse(jsonString);
                            System.out.println("----------" + jsonString);
                            ACLMessage reply = msg.createReply();

                            //Initializing rulebase
                            RuleBase rb = Ressources.AirAlgerieRuleBase();
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
                            System.out.println("dagi3");
                            rb.forwardChain(display);
                            System.out.println(rb.displayVariables());

                            reply.setContent("AN1 IS RESPONDING TO AGENT_CENTRAL");
                            myAgent.send(reply);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    else if(msg.getPerformative()==ACLMessage.CONFIRM){
                        //we enter here to confirm l'achat des billets
                        System.out.println("AN1 CONFIRMATION");
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
