@echo off
echo ------------------------------------------------------------------------
echo.                *** Master Service Stop ***
echo.
echo NOTE: This file has been generated automatically - do not edit!
echo.
echo Copyright (c) 2017 by MAster engine Ltda., RS, Brasil..
echo All rights reserved.
echo Created by: Tiago / Artur
echo.
echo.
echo ------------------------------------------------------------------------


taskkill /F /FI "SERVICES eq tomcat_master_engine"

