# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copy Maven files from subdirectory
COPY user-authentication/.mvn/ .mvn
COPY user-authentication/mvnw user-authentication/pom.xml ./

RUN apt-get update && apt-get install -y dos2unix && \
    dos2unix mvnw && chmod +x mvnw

RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY user-authentication/src ./src

RUN ./mvnw package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "app.jar"]
