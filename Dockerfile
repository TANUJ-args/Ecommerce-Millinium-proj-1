# 1. Use a lightweight Java Runtime as the base
FROM eclipse-temurin:21-jdk-alpine

# 2. Set the working directory inside the container
WORKDIR /app

# 3. Copy the compiled JAR file from your target folder to the container
# Note: You must run 'mvn clean package' first to generate this JAR!
COPY target/*.jar app.jar

# 4. Expose the port your Spring Boot app runs on
EXPOSE 8080

# 5. The command to run your app
ENTRYPOINT ["java", "-jar", "app.jar"]