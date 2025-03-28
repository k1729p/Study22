@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-24
set M2_HOME=c:\\tools\\apache-maven-3.9.9
set PROJECT=study22
set DOCKER_IMAGE=eeengcs/study22-acc-sender:1.0.0-SNAPSHOT
set CONTAINER_NAME=study22-acc-sender
set DOCKER_FILE=..\docker-config\Dockerfile.acc-sender
set COMPOSE_FILE=..\docker-config\compose-acc-sender.yaml

pushd %cd%
cd ..\account-sender
call %M2_HOME%\bin\mvn clean install
docker container rm --force %CONTAINER_NAME% --force > nul 2>&1
docker image rm --force %DOCKER_IMAGE% > nul 2>&1
docker build --file %DOCKER_FILE% --tag %DOCKER_IMAGE% .
docker run ^
   --network app-net ^
   --name %CONTAINER_NAME% ^
   --detach ^
   %DOCKER_IMAGE% ^
   without-authorization
popd
pause