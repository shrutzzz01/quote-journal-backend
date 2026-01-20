# Stage 1: Build the application using Maven and Temurin JDK 17
FROM maven:3.8.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application using the Temurin JRE 17 (smaller and faster)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# This copies the jar we built in the first stage
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx1g", "-Xms512m", "-jar", "app.jar"]