@echo off
setlocal
cd /d "%~dp0"
set "MYSQL=C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
if exist "%MYSQL%" goto :run
where mysql >nul 2>&1
if errorlevel 1 (
  echo MySQL Client nao encontrado. Verifique a instalacao do MySQL.
  pause
  exit /b 1
)
set "MYSQL=mysql"
:run
echo ========================================
echo  1 - PREPARAR BANCO DE DADOS
 echo ========================================
echo Digite a senha do MySQL quando solicitada.
echo O banco sera criado sem excluir dados existentes.
"%MYSQL%" -h localhost -P 3306 -u root -p < "sql\01_criar_banco.sql"
if errorlevel 1 (
  echo ERRO: Nao foi possivel preparar o banco.
  pause
  exit /b 1
)
echo Banco cadastro_pessoas pronto!
pause
