@echo off
set KIND=C:\tools\kind\kind.exe
set HELM=D:\tools\helm-v3.17.2\helm.exe
set KUBECTL=C:\PROGRA~1\Docker\Docker\resources\bin\kubectl.exe

set KEY=N
set /P KEY="Recreate 'kind' cluster? Y [N]"
if /i "%KEY:~0,1%"=="Y" (
   echo = = = = = = = = = = kind delete cluster = = = = = = = = = =
   %KIND% delete cluster > nul 2>&1
   echo = = = = = = = = = = kind create cluster = = = = = = = = = =
   %KIND% create cluster --config ..\kubernetes-config\kind-config.yaml
)

echo = = = = = = = = = = kind get clusters = = = = = = = = = =
%KIND% get clusters
echo.

set KEY=N
set /P KEY="Recreate Helm charts? Y [N]"
if /i "%KEY:~0,1%"=="Y" (
   echo = = = = = = = = = = Helm chart repositories = = = = = = = = = =
   %HELM% repo remove bitnami > nul 2>&1
   %HELM% repo add bitnami https://charts.bitnami.com/bitnami
   %HELM% repo update
   %HELM% repo list
   echo = = = = = = = = = = Helm chart releases = = = = = = = = = =
   %HELM% uninstall kp-kafka > nul 2>&1
   %HELM% uninstall kp-mongodb > nul 2>&1
   %HELM% uninstall kp-postgresql > nul 2>&1
   %KUBECTL% delete pvc -l release=kp-postgresql > nul 2>&1

   %HELM% install kp-kafka -f ..\kubernetes-config\authorization-kafka.yaml ^
                                oci://registry-1.docker.io/bitnamicharts/kafka
   %HELM% install kp-mongodb -f ..\kubernetes-config\authorization-mongodb.yaml ^
                                oci://registry-1.docker.io/bitnamicharts/mongodb
   %HELM% install kp-postgresql -f ..\kubernetes-config\authorization-postgresql.yaml ^
                                oci://registry-1.docker.io/bitnamicharts/postgresql
)
echo = = = = = = = = = = Helm charts list = = = = = = = = = =
%HELM% list

echo = = = = = = = = = = kind load docker-image = = = = = = = = = =
%KIND% load docker-image eeengcs/study22-acc-sender:1.0.0-SNAPSHOT --name kind
%KIND% load docker-image eeengcs/study22-acc-receiver:1.0.0-SNAPSHOT --name kind
echo = = = = = = = = = = kind-control-plane crictl images = = = = = = = = = =
docker exec -it kind-control-plane crictl images
echo = = = = = = = = = = kind-control-plane crictl pods = = = = = = = = = =
docker exec -it kind-control-plane crictl pods

set KEY=N
set /P KEY="Delete and apply pods? Y [N]"
if /i "%KEY:~0,1%"=="Y" (
   echo = = = = = = = = = = delete pods = = = = = = = = = =
   %KUBECTL% delete -f ..\kubernetes-config\\study22-acc-sender.yaml > nul 2>&1
   %KUBECTL% delete -f ..\kubernetes-config\\study22-acc-receiver.yaml > nul 2>&1
   echo = = = = = = = = = = apply pods = = = = = = = = = =
   %KUBECTL% apply -f ..\kubernetes-config\\study22-acc-sender.yaml
   echo - - - - This is the pause after 'sender' pod and before 'receiver' pod - - - -
   pause
   %KUBECTL% apply -f ..\kubernetes-config\\study22-acc-receiver.yaml
)

echo = = = = = = = = = = cluster-info = = = = = = = = = =
%KUBECTL% cluster-info --context kind-kind
echo.
echo = = = = = = = = = = get namespaces = = = = = = = = = =
%KUBECTL% get namespaces -o wide
echo.
echo = = = = = = = = = = get services = = = = = = = = = =
%KUBECTL% get services -o wide
echo.
echo = = = = = = = = = = get persistent volumes = = = = = = = = = =
%KUBECTL% get pv
echo.
echo = = = = = = = = = = get deployments = = = = = = = = = =
%KUBECTL% get deployments -o wide
echo.
echo = = = = = = = = = = get nodes = = = = = = = = = =
%KUBECTL% get nodes -o wide
echo.
echo = = = = = = = = = = get pods = = = = = = = = = =
%KUBECTL% get pods -o wide
echo.

set KEY=N
set /P KEY="Print a detailed description of the resources? Y [N]"
if /i "%KEY:~0,1%"=="Y" (
   echo = = = = = = = = = = api-resources = = = = = = = = = =
   %KUBECTL% api-resources
   echo.
   echo = = = = = = = = = = describe deployment = = = = = = = = = =
   %KUBECTL% describe deployment
   echo.
   echo = = = = = = = = = = describe services = = = = = = = = = =
   %KUBECTL% describe services
   echo = = = = = = = = = = describe nodes = = = = = = = = = =
   %KUBECTL% describe nodes
   echo = = = = = = = = = = describe pod 'kafka' = = = = = = = = = =
   %KUBECTL% describe pods -l app.kubernetes.io/name=kafka
   echo = = = = = = = = = = describe pod 'mongodb' = = = = = = = = = =
   %KUBECTL% describe pods -l app.kubernetes.io/name=mongodb
   echo = = = = = = = = = = describe pod 'postgresql' = = = = = = = = = =
   %KUBECTL% describe pods -l app.kubernetes.io/name=postgresql
   echo = = = = = = = = = = describe pod 'study22-acc-sender' = = = = = = = = = =
   %KUBECTL% describe pods -l app.kubernetes.io/name=study22-acc-sender
   echo = = = = = = = = = = describe pod 'study22-acc-receiver' = = = = = = = = = =
   %KUBECTL% describe pods -l app.kubernetes.io/name=study22-acc-receiver
)
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause