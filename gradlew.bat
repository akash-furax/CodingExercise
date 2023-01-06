@echo off

:: Compile
gradle compile

:: Build
gradle build

:: Run test cases
gradle test

:: Package
gradle package

:: Run the Spring Boot application
gradle bootRun