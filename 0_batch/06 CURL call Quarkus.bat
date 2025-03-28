@echo on
@set CURL=curl -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

@echo Curl could call Quarkus started on Kubernetes OR Quarkus started on Docker.
@set KEY=Y
@set /P KEY="Call Quarkus started on Kubernetes? [Y] N"
@if /i "%KEY:~0,1%"=="Y" (
  @set SITE=http://localhost:32123
) else (
  @set SITE=http://localhost:8080
)

%HR_YELLOW%
@powershell -Command Write-Host "GET 'accounts/a-a-a-a'" -foreground "Green"
%CURL% "%SITE%/accounts/a-a-a-a"
@echo.

%HR_YELLOW%
@powershell -Command Write-Host "GET 'accounts/a-a-a-z'" -foreground "Green"
%CURL% "%SITE%/accounts/a-a-a-z"
@echo.

%HR_YELLOW%
@set KEY=N
@set /P KEY="Delete all accounts in MongoDB and PostgreSQL? Y [N]"
@if /i "%KEY:~0,1%"=="Y" (
   %HR_YELLOW%
   @powershell -Command Write-Host "GET 'delete'" -foreground "Green"
   %CURL% -X DELETE "%SITE%/delete"
)
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause