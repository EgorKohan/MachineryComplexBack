FROM maven:3.8.5-openjdk-17

VOLUME /home/server

WORKDIR /home/server

COPY ./ /home/server/

EXPOSE 8080

CMD mvn clean install \
    && java -jar target/MachineryComplexBack-1.0-SNAPSHOT.jar