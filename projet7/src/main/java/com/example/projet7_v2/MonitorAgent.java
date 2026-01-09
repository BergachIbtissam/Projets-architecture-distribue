package com.example.projet7_v2;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Agent de surveillance (Heartbeat)
 */
public class MonitorAgent extends Agent {

    // Temps du dernier PONG reçu
    private long lastPongTime;

    // Timeout (5 secondes)
    private static final long TIMEOUT = 5000;

    @Override
    protected void setup() {
        System.out.println("👀 MonitorAgent démarré");

        lastPongTime = System.currentTimeMillis();

        // Envoi périodique de PING (toutes les 3 secondes)
        addBehaviour(new TickerBehaviour(this, 3000) {
            @Override
            protected void onTick() {
                ACLMessage ping = new ACLMessage(ACLMessage.REQUEST);
                ping.addReceiver(new AID("AgentPrincipal", AID.ISLOCALNAME));
                ping.setContent("PING");

                send(ping);
                System.out.println("➡️ PING envoyé à AgentPrincipal");
            }
        });

        // Réception des PONG
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null && "PONG".equals(msg.getContent())) {
                    lastPongTime = System.currentTimeMillis();
                    System.out.println("⬅️ PONG reçu");
                } else {
                    block();
                }
            }
        });

        // Vérification du timeout
        addBehaviour(new TickerBehaviour(this, 2000) {
            @Override
            protected void onTick() {
                if (System.currentTimeMillis() - lastPongTime > TIMEOUT) {
                    System.out.println("⚠️ PANNE détectée : AgentPrincipal ne répond plus");

                    // Activer le BackupAgent
                    activateBackupAgent();

                    stop(); // éviter répétition
                }
            }
        });
    }

    private void activateBackupAgent() {
        ACLMessage alert = new ACLMessage(ACLMessage.INFORM);
        alert.addReceiver(new AID("BackupAgent", AID.ISLOCALNAME));
        alert.setContent("ACTIVATE");

        send(alert);
        System.out.println("🚨 BackupAgent activé");
    }
}
