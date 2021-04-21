FROM openjdk:8-jdk-alpine as build
RUN apk add --no-cache maven
WORKDIR /java
COPY . /java
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:8-jdk-alpine as prod

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8090

COPY --from=build /java/target/rest-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
EXPOSE 8089
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
