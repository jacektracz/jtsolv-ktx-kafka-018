# Use official OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file
COPY build/libs/jtsolv-kafka-producer-0.0.1-SNAPSHOT.jar jtsolv-kafka-producer.jar

# Expose ports (8080 for REST API)
EXPOSE 50777

# Environment variables for Kafka broker
ENV KAFKA_BROKER=kafka-service:9092

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "jtsolv-kafka-producer.jar"]
