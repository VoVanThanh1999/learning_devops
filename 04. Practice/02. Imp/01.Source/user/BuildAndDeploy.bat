call echo "Step1: boot jar file"
call mvn clean install -DskipTests 
call echo "Step2: Build images from docker file"
call docker build -t btc/users:latest --force-rm -f Dockerfile .
call echo "Step3: run docker file"
call docker run -i -d -p 80:8080 --env-file secret/env btc/users:latest
