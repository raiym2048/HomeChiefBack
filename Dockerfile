# Stage 1: Build the application using Maven and JDK 21
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app
COPY src /app/src
COPY pom.xml /app/
COPY mvnw /app/
COPY .mvn /app/.mvn/
RUN chmod +x /app/mvnw

# Build the project (skip tests for faster builds)
RUN ./mvnw clean package -DskipTests

# Debug step: Verify the JAR file exists
RUN ls -l /app/target

# Stage 2: Run the application using JRE 21
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/HomeChiefBack-0.0.1-SNAPSHOT.jar /app/homechief.jar

# Debug step: Verify the JAR file exists in the runtime container
RUN ls -l /app

# Create necessary directories (e.g., uploads)
RUN mkdir -p /app/uploads

# Expose the port the app will run on
EXPOSE 8090

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/homechief.jar"]
