package com.example.projet7_v2;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Agent principal surveillé par MonitorAgent
 */
public class AgentPrincipal extends Agent {

    @Override
    protected void setup() {
        System.out.println("✅ AgentPrincipal démarré : " + getLocalName());

        // Répond aux PING par PONG
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null && "PING".equals(msg.getContent())) {
                    ACLMessage reply = msg.createReply();
                    reply.setContent("PONG");
                    send(reply);

                    System.out.println("📩 PING reçu → PONG envoyé");
                } else {
                    block();
                }
            }
        });

        // 🔴 Simulation automatique de panne après 20 secondes
        addBehaviour(new WakerBehaviour(this, 20000) {
            @Override
            protected void onWake() {
                System.out.println("💥 PANNE simulée : AgentPrincipal arrêté");
                doDelete(); // Arrêt de l'agent
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("❌ AgentPrincipal supprimé");
    }
}
