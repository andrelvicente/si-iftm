@echo off
setlocal

echo ==========================================
echo   TESTES AUTOMATIZADOS - VETERINARIOS   
echo ==========================================

:main_menu
echo.
echo Escolha uma opcao:
echo 1. Verificar se aplicacao esta rodando
echo 2. Executar todos os testes
echo 3. Executar teste especifico
echo 4. Iniciar aplicacao
echo 5. Sair

set /p choice="Digite sua escolha (1-5): "

if "%choice%"=="1" goto check_app
if "%choice%"=="2" goto run_all_tests
if "%choice%"=="3" goto run_specific_test
if "%choice%"=="4" goto start_app
if "%choice%"=="5" goto exit_script
echo ❌ Opcao invalida
goto main_menu

:check_app
echo Verificando se a aplicacao esta rodando...
curl -s http://localhost:8080/home >nul 2>&1
if %errorlevel%==0 (
    echo ✅ Aplicacao esta rodando em http://localhost:8080
) else (
    echo ❌ Aplicacao nao esta rodando
)
goto continue

:run_all_tests
echo Verificando se a aplicacao esta rodando...
curl -s http://localhost:8080/home >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️ Inicie a aplicacao primeiro (opcao 4)
    goto continue
)

echo.
echo 🔄 Executando todos os testes Selenium WebDriver...
cd gerenciador-Veterinarios
call mvnw.cmd test -Dtest="*SeleniumTest" -q
echo ✅ Testes concluidos!
cd ..
goto continue

:run_specific_test
echo Verificando se a aplicacao esta rodando...
curl -s http://localhost:8080/home >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️ Inicie a aplicacao primeiro (opcao 4)
    goto continue
)

echo.
echo Testes disponiveis:
echo 1. CadastrarVeterinarioSeleniumTest
echo 2. PesquisarVeterinarioSeleniumTest
echo 3. ExcluirVeterinarioSeleniumTest
echo 4. AlterarVeterinarioSeleniumTest
echo 5. ListarVeterinarioSeleniumTest

set /p test_choice="Digite o numero do teste (1-5): "

if "%test_choice%"=="1" set test_class=CadastrarVeterinarioSeleniumTest
if "%test_choice%"=="2" set test_class=PesquisarVeterinarioSeleniumTest
if "%test_choice%"=="3" set test_class=ExcluirVeterinarioSeleniumTest
if "%test_choice%"=="4" set test_class=AlterarVeterinarioSeleniumTest
if "%test_choice%"=="5" set test_class=ListarVeterinarioSeleniumTest

if not defined test_class (
    echo ❌ Opcao invalida
    goto continue
)

echo 🔄 Executando %test_class%...
cd gerenciador-Veterinarios
call mvnw.cmd test -Dtest="%test_class%" -q
echo ✅ Teste concluido!
cd ..
goto continue

:start_app
echo 🚀 Iniciando aplicacao...
cd gerenciador-Veterinarios
echo A aplicacao sera iniciada em http://localhost:8080
echo Pressione Ctrl+C para parar a aplicacao
call mvnw.cmd spring-boot:run
cd ..
goto continue

:continue
echo.
pause
goto main_menu

:exit_script
echo 👋 Ate logo!
exit /b 0
