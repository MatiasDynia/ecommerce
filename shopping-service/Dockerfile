FROM java:8-jre

ADD ./build/libs/shopping-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/shopping-service.jar"]

EXPOSE 9003