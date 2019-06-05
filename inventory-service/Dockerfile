FROM java:8-jre

ADD ./build/libs/inventory-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/inventory-service.jar"]

EXPOSE 8100