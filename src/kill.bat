@echo off
taskkill /f /im java.exe

rmdir /Q /S "r\work"
rmdir /Q /S "C:\Program Files\Apache Software Foundation\Apache-Tomcat-8.0.5\logs"
rmdir /Q /S "C:\Program Files\Apache Software Foundation\Apache-Tomcat-8.0.5\conf\Catalina"

mkdir "C:\Program Files\Apache Software Foundation\Apache-Tomcat-8.0.5\logs"