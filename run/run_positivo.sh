#!/bin/bash
echo "🔹 Ejecutando pruebas POSITIVAS"
allure results clean
mvn clean test -DsuiteXmlFile=src/test/resources/testng-positivo.xml
allure generate allure-results --clean -o allure-report
allure open allure-report
