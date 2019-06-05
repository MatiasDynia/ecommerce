FROM java:8-jre

ADD ./build/libs/monitoring-server.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/monitoring-server.jar"]

EXPOSE 4444