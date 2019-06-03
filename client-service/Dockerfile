FROM java:8-jre

ADD ./build/libs/client-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/client-service.jar"]

EXPOSE 9200