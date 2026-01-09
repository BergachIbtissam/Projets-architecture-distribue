package com.example.Project3;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.core.behaviours.OneShotBehaviour;

public class ClientAgent extends Agent {

    protected void setup() {
        System.out.println(getLocalName() + " lancé");

        addBehaviour(new OneShotBehaviour() {
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(new AID("resource", AID.ISLOCALNAME)); // nom du ResourceAgent
                msg.setContent("RESERVE");
                send(msg);
                System.out.println(getLocalName() + " : demande envoyée");

                // attendre la réponse
                ACLMessage reply = blockingReceive();
                System.out.println(getLocalName() + " : réponse reçue -> " + reply.getContent());
            }
        });
    }
}