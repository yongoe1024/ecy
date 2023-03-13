FROM amazoncorretto:17
ADD ./ecy-server/target/ecy-server.jar  /ecy/
EXPOSE 8081
ENTRYPOINT [ "bash","-c" ,"java -jar -Dfile.encoding=utf8 /ecy/ecy-server.jar"]
