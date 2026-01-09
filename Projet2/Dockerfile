# Image de base Java 17
FROM eclipse-temurin:17-jdk

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copier le JAR généré
COPY target/studentapi-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port de l’application
EXPOSE 8080

# Lancer l’application
ENTRYPOINT ["java", "-jar", "app.jar"]
