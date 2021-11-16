FROM openjdk:8-jdk-alpine
EXPOSE 8082
ADD target/devops-0.0.1-SNAPSHOT.war devops-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","/devops-0.0.1-SNAPSHOT.war"]
