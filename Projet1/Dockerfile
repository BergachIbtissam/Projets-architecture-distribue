# Image Java officielle maintenue (Eclipse Temurin)
FROM eclipse-temurin:17-jdk

# Dossier de travail dans le conteneur
WORKDIR /app

# Copier le fichier Java
COPY HelloWorld.java .

# Compiler le programme
RUN javac HelloWorld.java

# Exécuter le programme
CMD ["java", "HelloWorld"]
