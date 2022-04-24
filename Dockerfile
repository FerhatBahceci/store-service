FROM adoptopenjdk:16-jre-hotspot
COPY build/libs/store-service-0.0.1-SNAPSHOT-all.jar store-service-0.0.1-SNAPSHOT-all.jar
EXPOSE 50051
CMD ["java","-jar","store-service-0.0.1-SNAPSHOT-all.jar"]
