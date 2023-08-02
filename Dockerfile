# Use official base image of Java Runtime
FROM openjdk:17-jdk-slim

# The application's .jar file
ARG JAR_FILE=target/*.jar

# Copy the application's .jar to the container
COPY ${JAR_FILE} app.jar

# Run the app
ENTRYPOINT ["java","-jar","/app.jar"]