FROM adoptopenjdk:11-jre-hotspot
#VOLUME /tmp
WORKDIR /app
COPY target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
