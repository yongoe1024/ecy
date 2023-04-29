FROM amazoncorretto:17
MAINTAINER yongoe <121887765@qq.com>
# 时区问题
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
ADD ./ecy-server/target/ecy-server.jar  /ecy/
EXPOSE 8081
ENTRYPOINT [ "bash","-c" ,"java -jar -Dfile.encoding=utf8 /ecy/ecy-server.jar"]
