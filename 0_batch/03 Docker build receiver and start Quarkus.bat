@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-24
set M2_HOME=c:\\tools\\apache-maven-3.9.9
set DOCKER_FILE=../docker-config\Dockerfile.native
set DOCKER_IMAGE=eeengcs/study22-acc-receiver:1.0.0-SNAPSHOT
set CONTAINER_NAME=study22-acc-receiver
set JARFILE=target/Study22-account-receiver-1.0.0-SNAPSHOT-runner.jar

pushd %cd%
cd ..\account-receiver

set KEY=N
set /P KEY="Execute Maven goal 'test'? Y [N]"
if /i "%KEY:~0,1%"=="Y" (
   call %M2_HOME%\bin\mvn clean test
)
echo Now building native with Maven, building Docker image, and running on Docker
docker container rm --force %CONTAINER_NAME% --force > nul 2>&1
docker image rm --force %DOCKER_IMAGE% > nul 2>&1
call %M2_HOME%\bin\mvn clean install -Dnative -DskipTests ^
   -Dquarkus.native.container-build=true
docker build --file %DOCKER_FILE% --tag %DOCKER_IMAGE% .
docker run ^
   --interactive ^
   --net=app-net ^
   --publish 8080:8080 ^
   --env-file ..\docker-config\environment\account-receiver-env.properties ^
   --name %CONTAINER_NAME% ^
   %DOCKER_IMAGE%
popd
pause