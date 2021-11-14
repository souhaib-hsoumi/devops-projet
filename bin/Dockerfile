FROM openjdk:8
EXPOSE 8085
ADD /target/projetdevo-1.5.jar projetdevo-1.5.jar
ENTRYPOINT ["java","-jar","projetdevo-1.5.jar","--spring.config.name=prod"]