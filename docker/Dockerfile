#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY game_library-0.0.1-SNAPSHOT.jar /game_library.war

# run the app
CMD ["usr/bin/java", "-jar", "/game_library.war"]