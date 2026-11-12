@echo off
setlocal
cd /d "%~dp0"
echo ========================================
echo  3 - INICIAR CLIENTE
 echo ========================================
where javac >nul 2>&1
if errorlevel 1 (
  echo JDK nao encontrado. Instale um JDK com javac.
  pause
  exit /b 1
)
if not exist "Cliente\build\classes" mkdir "Cliente\build\classes"
javac -encoding UTF-8 -source 8 -target 8 -d "Cliente\build\classes" "Cliente\src\beans\Pessoa.java" "Cliente\src\cliente\Cliente.java"
if errorlevel 1 (
  echo Falha ao compilar o cliente.
  pause
  exit /b 1
)
java -cp "Cliente\build\classes" cliente.Cliente
if errorlevel 1 pause
