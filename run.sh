#!/bin/sh
dir=$( cd "$(dirname "${BASH_SOURCE[0]}")" && pwd);
cd $dir
mvn -T 8  -DskipTests clean package
if [ $? -ne 0 ]
then
  echo "============================";
  echo "            打包失败"
  echo "============================";
  exit
fi
docker rmi $(docker images -f "dangling=true" -q)
docker-compose down && docker-compose build --no-cache && docker-compose up
if [ $? -ne 0 ]
then
  echo "============================";
  echo "            docker启动失败"
  echo "============================";
  exit
fi

