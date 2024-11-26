FROM openjdk:17-jdk-slim
COPY build/libs/receipt-service-1.0.0.jar receipt-service.jar
ENTRYPOINT ["java", "-jar", "receipt-service.jar"]