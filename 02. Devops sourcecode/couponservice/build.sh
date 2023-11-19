#!/bin/bash
#echo "build jar app"
#mvn clean package -DskipTests 
echo "build image"
docker image rm -f coupon_app
docker build -t coupon_app .
echo "run app"
docker rm coupon-app
docker run -t --name=coupon-app --link docker-mysql:mysql -p 10555:9091 coupon_app
docker exec -it docker-mysql bash
