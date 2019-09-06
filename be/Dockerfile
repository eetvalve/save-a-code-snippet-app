FROM maven:3.6-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -D"spring-boot.run.profiles"=gcp

FROM gcr.io/distroless/java
COPY --from=build /usr/src/app/target/code-snippet-app-0.0.1-SNAPSHOT.jar /usr/app/code-snippet-app-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/code-snippet-app-0.0.1-SNAPSHOT.jar"]
