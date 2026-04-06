@echo off
REM =========================================================
REM Gradle start script for Windows
REM =========================================================
set DIRNAME=%~dp0
set APP_HOME=%DIRNAME%

if "%JAVA_HOME%" == "" (
  echo Please set JAVA_HOME
  exit /b 1
)

"%JAVA_HOME%\bin\java.exe" -classpath "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain %*
