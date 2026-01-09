package com.example.Projet4;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class RessourceAgent extends Agent {

    private boolean available = true; // État de la ressource
    private Queue<ACLMessage> queue = new LinkedList<>(); // File FIFO

    protected void setup() {
        System.out.println(getLocalName() + " lancé");

        // CyclicBehaviour pour recevoir toutes les demandes
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg = receive();
                if (msg != null && "RESERVE".equals(msg.getContent())) {
                    queue.add(msg);
                    System.out.println(msg.getSender().getLocalName() + " ajouté à la file");
                    printQueue();
                } else {
                    block();
                }
            }
        });

        // Lancer le traitement de la file après un petit délai pour que tous les agents aient le temps d'envoyer RESERVE
        addBehaviour(new WakerBehaviour(this, 1000) { // 1 seconde
            protected void onWake() {
                processQueue();
            }
        });
    }

    // Afficher la file actuelle
    private void printQueue() {
        String queueStr = queue.stream()
                .map(m -> m.getSender().getLocalName())
                .collect(Collectors.joining(","));
        System.out.println("File actuelle: [" + queueStr + "]");
    }

    // Traiter la file FIFO
    private void processQueue() {
        if (!available || queue.isEmpty()) return;

        ACLMessage msg = queue.poll(); // Retirer le premier de la file
        available = false;

        System.out.println("Le premier dans la file : " + msg.getSender().getLocalName());

        // Envoyer ACCEPTED
        ACLMessage reply = msg.createReply();
        reply.setContent("ACCEPTED");
        send(reply);
        System.out.println(msg.getSender().getLocalName() + " reçoit : ACCEPTED");
        System.out.println("Ressource réservée pour " + msg.getSender().getLocalName());

        printQueue(); // Afficher la file après retrait

        // Simulation de l'utilisation de la ressource (3 secondes)
        addBehaviour(new WakerBehaviour(this, 3000) {
            protected void onWake() {
                available = true;
                System.out.println("Ressource libérée");
                processQueue(); // Traiter le suivant
            }
        });
    }
}
