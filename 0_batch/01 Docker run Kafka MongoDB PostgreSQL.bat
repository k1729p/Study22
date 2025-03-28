@echo off
set KEY=N
set /P KEY="Recreate network? Y [N]"
if /i "%KEY:~0,1%"=="Y" (
   docker network rm --force app-net
   docker network create app-net
)

set KEY=Y
set /P KEY="Run Kafka container in Docker? [Y] N"
if /i "%KEY:~0,1%"=="Y" (
   docker container rm --force kp-kafka > nul 2>&1
   docker run --detach ^
      --name kp-kafka ^
      --hostname kp-kafka ^
      --network app-net ^
      --publish 9092:9092 ^
      --env-file ..\docker-config\environment\kafka-env.properties ^
      --detach ^
      bitnami/kafka:latest
)

set KEY=Y
set /P KEY="Run MongoDB container in Docker? [Y] N"
if /i "%KEY:~0,1%"=="Y" (
   docker container rm --force kp-mongodb > nul 2>&1
   docker run ^
      --name kp-mongodb ^
      --network app-net ^
      --publish 27017:27017 ^
      --env-file ..\docker-config\environment\mongodb-env.properties ^
      --detach ^
      mongodb/mongodb-community-server:latest
)

set KEY=Y
set /P KEY="Run PostgreSQL container in Docker? [Y] N"
if /i "%KEY:~0,1%"=="Y" (
   docker container rm --force kp-postgresql > nul 2>&1
   docker run ^
      --name kp-postgresql ^
      --network app-net ^
      --publish 5432:5432 ^
      --env-file ..\docker-config\environment\postgresql-env.properties ^
      --detach ^
      postgres
)
pause