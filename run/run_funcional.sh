#!/bin/bash
echo "🔹 Ejecutando pruebas FUNCIONALES"
allure results clean
mvn clean test -DsuiteXmlFile=src/test/resources/testng-funcional.xml
allure generate allure-results --clean -o allure-report
allure open allure-report
