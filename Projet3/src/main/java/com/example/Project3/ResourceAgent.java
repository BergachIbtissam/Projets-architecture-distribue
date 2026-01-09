package com.example.Project3;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class ResourceAgent extends Agent {
    private boolean available = true; // État de la ressource

    protected void setup() {
        System.out.println(getLocalName() + " lancé"); // Affiche que l'agent démarre

        // Comportement cyclique pour écouter les messages
        addBehaviour(new CyclicBehaviour() {
            public void action() {
            	
                ACLMessage msg = receive(); // Récupère un message
                if (msg != null) { // Si un message est reçu
                    if (msg.getContent().equals("RESERVE")) { // Si on veut réserver
                        ACLMessage reply = msg.createReply(); // Crée une réponse
                        if (available) { // Si la ressource est disponible
                            available = false; // On la réserve
                            reply.setContent("ACCEPTED"); // On accepte la requête
                        } else {
                            reply.setContent("REFUSED"); // Sinon on refuse
                        }
                        send(reply); // Envoie la réponse
                    }
                } else {
                    block(); // Pas de message, on attend
                }
            }
        });
    }
}
