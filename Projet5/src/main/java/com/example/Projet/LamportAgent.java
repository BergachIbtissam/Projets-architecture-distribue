package com.example.Projet;


import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class LamportAgent extends Agent {

    private int clock = 0;

    @Override
    protected void setup() {
        System.out.println(getLocalName() + " démarré | clock = " + clock);

        // Réception des messages
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    handleReceive(msg);
                } else {
                    block();
                }
            }
        });

        // Scénario d'envoi (uniquement au démarrage)
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                scenario();
            }
        });
    }

    // Envoyer un message
    private void sendMessage(String receiver, String content) {
        clock++;

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));

        // contenu|clock
        msg.setContent(content + "|" + clock);

        send(msg);

        System.out.println(getLocalName() +
                " envoie '" + content +
                "' avec horloge = " + clock +
                " à " + receiver);
    }

    // 📥 Recevoir un message
    private void handleReceive(ACLMessage msg) {
        String[] parts = msg.getContent().split("\\|");
        String content = parts[0];
        int receivedClock = Integer.parseInt(parts[1]);

        clock = Math.max(clock, receivedClock) + 1;

        System.out.println(getLocalName() +
                " reçoit '" + content +
                "' avec horloge = " + receivedClock +
                " → horloge locale = " + clock);

        compareClocks(receivedClock);
    }

    // 🔁 Comparaison des horloges
    private void compareClocks(int otherClock) {
        if (clock > otherClock) {
            System.out.println("→ Événement local APRÈS l'événement reçu");
        } else if (clock < otherClock) {
            System.out.println("→ Événement local AVANT l'événement reçu");
        } else {
            System.out.println("→ Événements CONCURRENTS");
        }
    }

    // 🎬 Scénario (équivalent à ton main)
    private void scenario() {
        switch (getLocalName()) {
            case "A":
                sendMessage("B", "Message A→B");
                break;
            case "B":
                sendMessage("C", "Message B→C");
                break;
            case "C":
                sendMessage("D", "Message C→D");
                break;
            case "D":
                sendMessage("A", "Message D→A");
                break;
        }
    }
}