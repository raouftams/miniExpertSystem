package app.jade;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

public class CentralAgent extends Agent {
    Object[] obj=null;
    Object simpleObj=null;
    Object[] arguments;

    protected void setup(){
        try{
            arguments= this.getArguments();
            DFAgentDescription dfd= new DFAgentDescription();
            dfd.setName(this.getAID());
            DFService.register(this,dfd);

            //creating information object to send to companies
            obj=new Object[arguments.length];
            for(int i=0;i<arguments.length;i++){
                obj[i]=arguments[i];
            }

            //Message sending
            for (int i = 0; i < 3; i++) {
                ACLMessage message= new ACLMessage(ACLMessage.INFORM);
                message.setContentObject(obj);
                String s="AN"+(i+1);
                System.out.println("data sent to " + s);
                message.addReceiver(new AID(s,AID.ISLOCALNAME));
                send(message);
            }


        }catch (Exception e){
            System.out.println(e);
        }

        addBehaviour(new CyclicBehaviour(this) {
            public void action(){
                //we wait for annexes to return their result
                ACLMessage msg = receive();
                if (msg != null) {
                    if (msg.getPerformative()==ACLMessage.INFORM){

                        try {
                            System.out.println("printing in AC : ");
                            simpleObj=msg.getContent();
                            System.out.println(simpleObj);

                            //new message to the annexe we chose, probably better if its a function
                            ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
                            msg1.setContent(msg.getContent());
                            //send to selected annexe
                            msg1.addReceiver(new AID("GUI", AID.ISLOCALNAME));
                            send(msg1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Do delete stops the cyclic behaviour, kills the agent.
                        //doDelete();
                    }

                    else {
                        //receive message from selected annexe, that l'achat is accepted
                        simpleObj=msg.getContent();
                        System.out.println(simpleObj);

                    }

                }
                else {
                    //this blocks the agent until it gets a message
                    block();
                }
            }
        });
    }
}
