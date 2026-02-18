@echo off
setlocal enabledelayedexpansion

rmdir /s /q build 2>nul
mkdir build

rem Compile ALL java sources under src\main\java
for /r "src\main\java" %%f in (*.java) do (
  set "SOURCES=!SOURCES! "%%f""
)

javac -encoding UTF-8 -d build !SOURCES!
if errorlevel 1 exit /b 1

java -cp build app.Main %*

@REM  .\run.bat simple
@REM  .\run.bat communes