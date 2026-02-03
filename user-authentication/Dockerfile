# Stage 1: Build the application
FROM eclipse-temurin:25-jdk AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project configuration
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Fix line endings and grant execution permissions to the Maven wrapper
# This ensures it runs correctly regardless of the host OS
RUN apt-get update && apt-get install -y dos2unix && \
    dos2unix mvnw && \
    chmod +x mvnw

# Download dependencies (offline mode to speed up subsequent builds)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application, skipping tests for faster image creation
RUN ./mvnw package -DskipTests -B

# Stage 2: Runtime image
FROM eclipse-temurin:25-jre

# Set the working directory
WORKDIR /app

# Copy the compiled JAR file from the build stage
# Using a wildcard to match the generated JAR name
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (matching server.port in application.properties)
EXPOSE 8181

# Set the entrypoint to run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
