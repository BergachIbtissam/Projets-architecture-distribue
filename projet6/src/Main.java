import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.ContainerController;

public class Main {

    public static void main(String[] args) {

        // Nombre d'agents à créer
        int numberOfAgents = 3; // tu peux augmenter ce nombre facilement

        // Initialiser JADE
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_PORT, "1200"); // port du Main Container
        ContainerController container = rt.createMainContainer(p);

        try {
            // Créer les agents avec IDs uniques
            for (int i = 1; i <= numberOfAgents; i++) {
                container.createNewAgent(
                        "Agent" + i,                 // Nom de l'agent
                        "agents.CoordinatorAgent",   // Classe de l'agent
                        new Object[]{String.valueOf(i)} // Arguments : ID
                ).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
