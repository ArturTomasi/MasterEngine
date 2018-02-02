@echo off
echo ------------------------------------------------------------------------
echo.                *** SA Service Start ***
echo.
echo NOTE: This file has been generated automatically - do not edit!
echo.
echo Copyright (c) 2017 by Master Engine Ltda., RS, Brasil..
echo All rights reserved.
echo.
echo Created by: Tiago / Artur
echo.
echo ------------------------------------------------------------------------

set instance=C:\master_engine\
set tools=C:\master_engine\tools\
set tomcat=C:\master_engine\tomcat
set runtime=C:\master_engine\runtime
set service_name=tomcat_master_engine


echo.
echo.

echo ----------------------------------------------
echo -- Importando dados e Iniciando o Serviço
echo ----------------------------------------------

echo.
echo.

rd /s /q %tomcat%\conf\Catalina
rd /s /q %tomcat%\logs
rd /s /q %tomcat%\temp
rd /s /q %tomcat%\work


mkdir %tomcat%\conf\Catalina
mkdir %tomcat%\logs
mkdir %tomcat%\temp
mkdir %tomcat%\work

xcopy /s /q /y %runtime%\* %tomcat%\webapps\eng\

net start %service_name%

goto end

:error_1
echo ----------------------------------------------
echo -- Servico %service_name% ja iniciado!
echo ----------------------------------------------

goto end

:end
