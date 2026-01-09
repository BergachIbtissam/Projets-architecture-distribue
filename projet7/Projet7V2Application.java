package com.example.projet7_v2;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;

public class Projet7V2Application {

    public static void main(String[] args) {
        try {
            Runtime rt = Runtime.instance();
            ProfileImpl p = new ProfileImpl();
            p.setParameter(ProfileImpl.MAIN_PORT, "1100");

            AgentContainer container = rt.createMainContainer(p);

            container.createNewAgent(
                    "MainAgent",
                 AgentPrincipal.class.getName(),
                    null
            ).start();

            container.createNewAgent(
                    "MonitorAgent",
                    MonitorAgent.class.getName(),
                    null
            ).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}