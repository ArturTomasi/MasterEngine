@echo off
echo ------------------------------------------------------------------------
echo.                *** Master Update ***
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
set backup=C:\master_engine\backup\ENG_%date:~-4%%date:~-7,2%%date:~-10,2%_%time:~0,2%%time:~3,2%%time:~6,2%
set backup=%backup: =0%
set tools=C:\master_engine\tools\
set webapps=C:\master_engine\tomcat\webapps\eng
set update=C:\master_engine\update\
set service_name=tomcat_master_engine



rem -- Verifica se o arquivo eng.war existe na pasta de update
:validate_update
if exist %update%eng.war goto update
if not exist %update%eng.war goto error_2

:update
echo --------------------------
echo -- Realizando backup    --
echo --------------------------
echo.
move /y %webapps% %backup%
cd %backup%
%tools%\zip.exe -r %backup% *
cd %instance%\bin
rd /s /q %backup%

echo.
echo.

echo ---------------------------
echo -- Extraindo atualizacao --
echo ---------------------------
echo.

mkdir %webapps%

%tools%unzip.exe -o %update%eng.war -d %webapps%

echo.
echo.


echo ----------------------------------------------
echo -- Iniciando o serviço do Tomcat --
echo ----------------------------------------------


call tc1s.bat

:end
