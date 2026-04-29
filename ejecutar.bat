@echo off
set JAVA_HOME=C:\Users\ortiz\Java\jdk-21.0.10+7
set MYSQL_HOME=C:\Program Files\MySQL\MySQL Server 8.4
set MYSQL_DATA=C:\Users\ortiz\mysql-data
set PATH=%JAVA_HOME%\bin;C:\Users\ortiz\Maven\apache-maven-3.9.14\bin;%MYSQL_HOME%\bin;%PATH%
cd /d "%~dp0"

echo Verificando MySQL...
mysql -u root -pCarpinteria2025! --connect-timeout=3 -e "SELECT 1;" >nul 2>&1
if %errorlevel% neq 0 (
    echo Iniciando MySQL...
    start /B "%MYSQL_HOME%\bin\mysqld.exe" --datadir="%MYSQL_DATA%" --port=3306
    timeout /t 8 /nobreak >nul
    echo MySQL listo.
) else (
    echo MySQL ya estaba corriendo.
)

echo Iniciando Sistema de Carpinteria...
echo Una vez que veas "Started CarpinteriaApplication" abre: http://localhost:8080
mvn spring-boot:run
pause
