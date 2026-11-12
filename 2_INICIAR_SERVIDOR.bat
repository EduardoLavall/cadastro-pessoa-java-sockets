@echo off
setlocal
cd /d "%~dp0"
echo ========================================
echo  2 - INICIAR SERVIDOR
 echo ========================================
where javac >nul 2>&1
if errorlevel 1 (
  echo JDK nao encontrado. Instale um JDK com javac.
  pause
  exit /b 1
)
if not exist "Servidor\build\classes" mkdir "Servidor\build\classes"
javac -encoding UTF-8 -source 8 -target 8 -d "Servidor\build\classes" "Servidor\src\beans\Pessoa.java" "Servidor\src\conexao\Conexao.java" "Servidor\src\dao\PessoaDAO.java" "Servidor\src\servidor\AtenderCliente.java" "Servidor\src\servidor\Servidor.java"
if errorlevel 1 (
  echo Falha ao compilar o servidor.
  pause
  exit /b 1
)
java -cp "Servidor\build\classes;Servidor\lib\*" servidor.Servidor
pause
