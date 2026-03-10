FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY target/Ecommerce.jar Ecommerce.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","Ecommerce.jar"]