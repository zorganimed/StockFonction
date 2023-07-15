FROM openjdk:17-jdk

COPY ./target/stock-0.0.1-SNAPSHOT.jar usr/app/stock.jar

WORKDIR usr/app

EXPOSE 8086

ENTRYPOINT ["java", "-jar" , "stock.jar"]
