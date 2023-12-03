call echo "Step1: boot jar file"
call mvn clean install -DskipTests 
call echo "Build images from docker file"
call docker build -t btc/news:latest --force-rm -f Dockerfile .
call docker run -i -d -p 8081:8081 --env-file secret/env btc/news:latest

