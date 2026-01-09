# Image de base Java 17 maintenue (Eclipse Temurin)
FROM eclipse-temurin:17-jdk

# Copie du JAR dans l'image
COPY target/tp-jade-0.0.1-SNAPSHOT.jar app.jar

# Port exposé
EXPOSE 8080

# Commande de démarrage
CMD ["java", "-jar", "app.jar"]
