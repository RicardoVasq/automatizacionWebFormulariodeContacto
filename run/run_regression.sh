#!/bin/bash
echo "🔹 Ejecutando pruebas de REGRESIÓN"
allure results clean
mvn clean test -DsuiteXmlFile=src/test/resources/testng-regression.xml
allure generate allure-results --clean -o allure-report
allure open allure-report
