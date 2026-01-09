package com.example.Projet4;


import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class MainJade {
    public static void main(String[] args) throws Exception {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createMainContainer(p);

        // Créer l'agent ressource
        AgentController resource = cc.createNewAgent("resource",
                "com.example.Projet4.RessourceAgent", null);
        resource.start();

        // Créer plusieurs étudiants
        String[] students = {"std1", "std2", "std3", "std4", "std5"};
        for (String s : students) {
            AgentController student = cc.createNewAgent(s,
                    "com.example.Projet4.StudentAgent", null);
            student.start();
        }
    }
}

