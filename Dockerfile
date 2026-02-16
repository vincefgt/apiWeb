FROM ubuntu:latest
LABEL authors="vinceFgt"
# Étape de build : compilation avec Maven
# Étape de runtime : exécution de l'application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY /target/*.jar /app/app.jar

# Exposer le port Spring Boot
EXPOSE 8085

# Démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
