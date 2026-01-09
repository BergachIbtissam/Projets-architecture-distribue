package com.example.projet7_v2;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class MonitorAgent extends Agent {

    long lastPong = System.currentTimeMillis();

    protected void setup() {
        System.out.println("MonitorAgent lancé");

        // Envoyer PING chaque 2 secondes
        addBehaviour(new TickerBehaviour(this, 2000) {
            protected void onTick() {
                ACLMessage ping = new ACLMessage(ACLMessage.REQUEST);
                ping.setContent("PING");
                ping.addReceiver(new AID("MainAgent", AID.ISLOCALNAME));
                send(ping);
                System.out.println("MonitorAgent envoyé: PING");
            }
        });

        // Réception des PONG
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && "PONG".equals(msg.getContent())) {
                    System.out.println("MonitorAgent reçu: PONG");
                    lastPong = System.currentTimeMillis();
                } else block();

                // Détection de panne si pas de PONG depuis 4 secondes
                if (System.currentTimeMillis() - lastPong > 4000) {
                    System.out.println("PANNE détectée");
                    startBackup();
                    doDelete();
                }
            }
        });
    }

    void startBackup() {
        try {
            getContainerController()
                    .createNewAgent("BackupAgent",
                            BackupAgent.class.getName(), null)
                    .start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
