FROM adoptopenjdk:11-jre-hotspot
COPY build/libs/store-service-0.0.1-SNAPSHOT.jar store-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-Xmx128m"]
