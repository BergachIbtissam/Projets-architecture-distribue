package com.example.Projet4;



import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;

public class StudentAgent extends Agent {

    protected void setup() {
        System.out.println(getLocalName() + " prêt");

        // Envoyer RESERVE immédiatement
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("resource", AID.ISLOCALNAME));
                msg.setContent("RESERVE");
                send(msg);
                System.out.println(getLocalName() + " a envoyé RESERVE");

                // Attendre le message ACCEPTED
                ACLMessage reply = blockingReceive();
                if (reply != null) {
                    System.out.println(getLocalName() + " reçoit : " + reply.getContent());
                }
            }
        });
    }
}
