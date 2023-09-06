# Use the official OpenJDK base image
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR/WAR file from your Spring Boot build output directory to the container
COPY target/gui-1.0.jar app.jar

# Expose the port your Spring Boot application listens on
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
