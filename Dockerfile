FROM maven:3-jdk-8 as builder

COPY . /opt/microservice-project

WORKDIR /opt/microservice-project

RUN mvn dependency:go-offline && mvn package -Dmaven.test.skip=true

FROM openjdk:8-alpine

USER root

COPY --from=builder /opt/microservice-project/target/projects-0.0.1-SNAPSHOT.jar /usr/src/projects-0.0.1-SNAPSHOT.jar

RUN  chmod +x  /usr/src/projects-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "/usr/src/projects-0.0.1-SNAPSHOT.jar"]
