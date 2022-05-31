FROM egorkokhan/maven-jdk17-gams:v1

ENV PathToGams=/opt/gams/gams39.1_linux_x64_64_sfx
ENV pathToGamsApi=${PathToGams}/apifiles/Java/api
ENV PATH=${PathToGams}:$PATH

VOLUME /home/server

WORKDIR /home/server

COPY ./ /home/server/

EXPOSE 8080

RUN mvn install:install-file \
    -Dfile=$pathToGamsApi/GAMSJavaAPI.jar \
    -DgroupId=com.gams.api \
    -DartifactId=gams \
    -Dversion=39 \
    -Dpackaging=jar \
    -Dpackaging=jar \
    && mvn -Dgams.path=$pathToGamsApi clean install

CMD java -Dspring.profiles.active=dev -Dgams.path=$pathToGamsApi -jar target/MachineryComplexBack-1.0-SNAPSHOT.jar