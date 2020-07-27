FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/tasktracker-v2.jar tasktracker.jar
ENTRYPOINT ["java","-jar","/tasktracker.jar"]