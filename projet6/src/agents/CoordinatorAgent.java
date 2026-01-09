package agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CoordinatorAgent extends Agent {

    private int id;                      // ID unique de l'agent
    private int leaderId = -1;           // ID du leader actuel
    private boolean isLeader = false;    // true si cet agent est le leader
    private boolean electionInProgress = false; // empêche de lancer plusieurs élections en même temps.

    @Override
    protected void setup() {
        // Récupérer l'ID passé depuis Main
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            id = Integer.parseInt(args[0].toString());
        }

        System.out.println(getLocalName() + " démarré avec ID = " + id);

        // Ajouter le comportement principal
        addBehaviour(new BullyBehaviour());

        // Lancer une élection au démarrage
        startElection();
    }

    // Méthode pour récupérer l'ID
    public int getId() {
        return id;
    }

    // Déclencher une élection
    public void startElection() {
        if (electionInProgress || isLeader) return;

        electionInProgress = true;
        System.out.println(getLocalName() + " lance une élection");

        boolean higherIdExists = false;

        // Envoyer message ELECTION aux agents ayant ID plus grand
        for (int i = 1; i <= 3; i++) {
            if (i > id) {
                ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                msg.addReceiver(new AID("Agent" + i, AID.ISLOCALNAME));
                msg.setContent("ELECTION");
                send(msg);
                higherIdExists = true;
            }
        }

        // Si aucun agent avec ID supérieur → je deviens leader
        if (!higherIdExists) {
            becomeLeader();
        }
    }

    // Déclarer cet agent comme leader
    private void becomeLeader() {
        isLeader = true;
        electionInProgress = false;
        leaderId = id;

        System.out.println(">>> " + getLocalName() + " EST LE LEADER <<<");

        // Informer tous les autres agents
        for (int i = 1; i <= 3; i++) {
            if (i != id) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("Agent" + i, AID.ISLOCALNAME));
                msg.setContent("COORDINATOR:" + id);
                send(msg);
            }
        }
    }

    // Comportement principal pour gérer les messages
    private class BullyBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage msg = receive();

            if (msg != null) {
                if ("ELECTION".equals(msg.getContent())) {
                    handleElection(msg);
                } else if (msg.getContent().startsWith("COORDINATOR")) {
                    handleCoordinator(msg);
                }
            } else {
                block();
            }
        }

        // Réagir aux messages ELECTION
        private void handleElection(ACLMessage msg) {
            int senderId = Integer.parseInt(
                    msg.getSender().getLocalName().replaceAll("\\D", "")
            );

            if (id > senderId) {
                System.out.println(getLocalName() +
                        " (ID=" + id + ") répond à ELECTION de " + senderId);
                startElection();
            }
        }

        // Réagir aux messages COORDINATOR
        private void handleCoordinator(ACLMessage msg) {
            leaderId = Integer.parseInt(msg.getContent().split(":")[1]);
            isLeader = (leaderId == id);
            electionInProgress = false;

            System.out.println(getLocalName() +
                    " reconnaît le leader : Agent" + leaderId);
        }
    }
}
