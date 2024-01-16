@echo off
set KUBECTL=C:\PROGRA~1\Docker\Docker\resources\bin\kubectl.exe

echo = = = = = = = = = = log for 'kp-kafka-controller-0' = = = = = = = = = =
%KUBECTL% logs kp-kafka-controller-0 --tail 3
echo.
echo = = = = = = = = = = log for 'kp-kafka-controller-1' = = = = = = = = = =
%KUBECTL% logs kp-kafka-controller-1 --tail 3
echo.
echo = = = = = = = = = = log for 'kp-kafka-controller-2' = = = = = = = = = =
%KUBECTL% logs kp-kafka-controller-2 --tail 3
echo.
echo = = = = = = = = = = log for 'mongodb' = = = = = = = = = =
%KUBECTL% logs -l app.kubernetes.io/name=mongodb --tail 3
echo.
echo = = = = = = = = = = log for 'postgresql' = = = = = = = = = =
%KUBECTL% logs -l app.kubernetes.io/name=postgresql --tail 3
echo.
echo = = = = = = = = = = log for 'study22-acc-sender' = = = = = = = = = =
%KUBECTL% logs -l app.kubernetes.io/name=study22-acc-sender --tail 5
echo.
echo = = = = = = = = = = log for 'study22-acc-receiver' = = = = = = = = = =
%KUBECTL% logs -l app.kubernetes.io/name=study22-acc-receiver --tail 20
echo.
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause