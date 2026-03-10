FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy project files
COPY . .

# Build the jar
RUN ./mvnw clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/*.jar"]