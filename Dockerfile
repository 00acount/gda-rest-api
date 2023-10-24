# Use a base image with Maven 4 and OpenJDK 17 pre-installed
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project's POM and source code files into the container
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application using Maven
RUN mvn clean install

# Create a minimal runtime image
FROM eclipse-temurin:17-jre

# Set the working directory in the runtime container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime container
COPY --from=build /app/target/*.jar ./app.jar

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

ENV FRONTEND_DOMAIN=""
ENV DB_HOST=""
ENV DB_USER=""
ENV DB_PASSWORD=""
ENV JWT_SECRET_KEY=""

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]
