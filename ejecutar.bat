@echo off
set JAVA_HOME=C:\Users\ortiz\Java\jdk-21.0.10+7
set PATH=%JAVA_HOME%\bin;C:\Users\ortiz\Maven\apache-maven-3.9.14\bin;%PATH%
cd /d "%~dp0"
echo Iniciando Sistema de Carpinteria...
echo Una vez que veas "Started CarpinteriaApplication" abre: http://localhost:8080/solicitudes
mvn spring-boot:run
pause
