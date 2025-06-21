# ---- Build Stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only necessary files first to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application (skip tests if needed)
RUN mvn clean package -DskipTests

# ---- Run Stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/purchase-service-*.jar app.jar

# Expose the application port (Spring Boot default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]