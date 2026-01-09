package com.example.projet7_v2;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Agent de secours activé en cas de panne
 */
public class BackupAgent extends Agent {

    private boolean active = false;

    @Override
    protected void setup() {
        System.out.println("🟡 BackupAgent prêt (en attente)");

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null && "ACTIVATE".equals(msg.getContent())) {
                    active = true;
                    System.out.println("✅ BackupAgent ACTIVÉ");
                    startService();
                } else {
                    block();
                }
            }
        });
    }

    // Le BackupAgent reprend le rôle de l'agent principal
    private void startService() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null && "PING".equals(msg.getContent())) {
                    ACLMessage reply = msg.createReply();
                    reply.setContent("PONG");
                    send(reply);

                    System.out.println("🔁 BackupAgent répond au PING");
                } else {
                    block();
                }
            }
        });
    }
}
