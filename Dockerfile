# Use OpenJDK base image
FROM openjdk:8-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/tracker-0.0.1-SNAPSHOT.jar tracker.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "tracker.jar"]