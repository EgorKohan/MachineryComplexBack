FROM egorkokhan/maven-jdk17-gams:v1

ENV PathToGams=/opt/gams/gams39.1_linux_x64_64_sfx
ENV pathToGamsApi=${PathToGams}/apifiles/Java/api

VOLUME /home/server

WORKDIR /home/server

COPY ./ /home/server/

EXPOSE 8080

CMD export PATH=$PathToGams:$PATH \
    && echo $PATH \
    && mvn install:install-file \
    -Dfile=$pathToGamsApi/GAMSJavaAPI.jar \
    -DgroupId=com.gams.api \
    -DartifactId=gams \
    -Dversion=39 \
    -Dpackaging=jar \
    && mvn -Dgams.path=$pathToGamsApi clean install \
    && java -Dgams.path=$pathToGamsApi -jar target/MachineryComplexBack-1.0-SNAPSHOT.jar