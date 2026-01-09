package com.example.projet7_v2;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class AgentPrincipal extends Agent {
    protected void setup() {
        System.out.println("AgentPrincipal lancé");

        // Répondre toujours aux PING
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && "PING".equals(msg.getContent())) {
                    System.out.println(" AgentPrincipal reçu: PING");
                    ACLMessage reply = msg.createReply();
                    reply.setContent("PONG");
                    send(reply);
                    System.out.println("AgentPrincipal envoyé: PONG");
                } else block();
            }
        });

        // Simuler panne après 8 secondes
        addBehaviour(new WakerBehaviour(this, 8000) {
            protected void onWake() {

                doDelete();
            }
        });
    }
}
