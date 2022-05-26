FROM amazoncorretto:11-alpine

RUN addgroup -S teambuilder && adduser -S teambuilder -G teambuilder
USER teambuilder:teambuilder

COPY ./build/libs/team-builder*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]