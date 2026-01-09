package com.example.projet7_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale Spring Boot
 * Lance l'application + JADE
 */
@SpringBootApplication
public class Projet7V2Application {

    public static void main(String[] args) {

        // Démarrage de Spring Boot
        SpringApplication.run(Projet7V2Application.class, args);

        // Démarrage de JADE et des agents
        JadeLauncher.startJadeAgents();
    }
}
