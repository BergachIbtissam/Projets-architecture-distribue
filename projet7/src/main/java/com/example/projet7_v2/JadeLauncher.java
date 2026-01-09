package com.example.projet7_v2;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * Classe responsable du lancement de JADE
 * et de la création des agents
 */
public class JadeLauncher {

    public static void startJadeAgents() {
        try {
            // Récupérer l'instance JADE
            Runtime runtime = Runtime.instance();

            // Configuration du profil JADE
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.GUI, "false"); // Pas d'interface graphique

            // Création du container principal
            ContainerController container =
                    runtime.createMainContainer(profile);

            // Création des agents
            AgentController monitor =
                    container.createNewAgent(
                            "MonitorAgent",
                            "com.example.projet7_v2.MonitorAgent",
                            null
                    );

            AgentController principal =
                    container.createNewAgent(
                            "AgentPrincipal",
                            "com.example.projet7_v2.AgentPrincipal",
                            null
                    );

            AgentController backup =
                    container.createNewAgent(
                            "BackupAgent",
                            "com.example.projet7_v2.BackupAgent",
                            null
                    );

            // Démarrage des agents
            monitor.start();
            principal.start();
            backup.start();

            System.out.println("✅ Agents JADE démarrés avec succès");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
