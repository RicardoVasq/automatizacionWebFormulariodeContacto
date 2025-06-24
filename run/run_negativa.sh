#!/bin/bash
echo "🔹 Ejecutando pruebas NEGATIVAS"
allure results clean
mvn clean test -DsuiteXmlFile=src/test/resources/testng-negativa.xml
allure generate allure-results --clean -o allure-report
allure open allure-report
