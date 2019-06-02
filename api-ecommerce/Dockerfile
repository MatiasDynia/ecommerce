FROM java:8-jre

ADD ./build/libs/api-ecommerce.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/api-ecommerce.jar"]

EXPOSE 9000