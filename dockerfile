FROM openjdk:17-jdk

COPY ./target/fonctionstk.jar usr/app/fonctionstk.jar

WORKDIR usr/app

EXPOSE 8086

ENTRYPOINT ["java", "-jar" , "fonctionstk.jar"]
