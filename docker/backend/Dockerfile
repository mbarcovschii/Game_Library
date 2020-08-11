# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY game-library-api-*.jar /game-library-api.war

# run the app
CMD ["usr/bin/java", "-jar", "/game-library-api.war"]