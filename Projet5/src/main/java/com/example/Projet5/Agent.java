package com.example.Projet5;

class Agent {
    private String name;
    private int clock = 0;

    public Agent(String name) {
        this.name = name;
    }

    
    // Envoyer un message à un autre agent
    public void sendMessage(Agent receiver, String content) {
        clock++;
        Message msg = new Message(name, content, clock);
        System.out.println(name + " envoie '" + content + "' avec horloge = " + clock + " à " + receiver.getName());
        receiver.receiveMessage(msg);
    }

 // Recevoir un message
    public void receiveMessage(Message msg) {
        // Mettre à jour l'horloge locale selon Lamport
        clock = Math.max(clock, msg.clock) + 1;
        System.out.println(name + " reçoit '" + msg.content + "' avec horloge = " + msg.clock + 
                           " → horloge locale mise à jour = " + clock);
        compareClocks(msg.clock); // Comparaison après réception
    }

    public String getName() {
        return name;
    }
    
 // Fonction pour comparer les horloges et décider de l'ordre
    public void compareClocks(int otherClock) {
        if (clock > otherClock) {
            System.out.println("→ L'événement local est **après** l'événement reçu (clock local > clock reçu)");
        } else if (clock < otherClock) {
            System.out.println("→ L'événement local est **avant** l'événement reçu (clock local < clock reçu)");
        } else {
            System.out.println("→ Les deux événements sont **concurrentiels** (clock local = clock reçu)");
        }
    }
}