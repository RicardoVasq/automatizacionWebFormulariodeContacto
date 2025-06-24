#!/bin/bash
cd "$(dirname "$0")/.." || exit
echo "🔸 Ejecutando TODOS los tests"
echo "🧹 Limpiando resultados anteriores de Allure..."
allure results clean

# Ejecutar todos los tests (sin filtrar por grupo)
mvn clean test

# Verificar si hay resultados
if [ -d "allure-results" ] && [ "$(ls -A allure-results)" ]; then
    echo "📊 Generando reporte Allure..."
    allure generate allure-results --clean -o allure-report
    echo "🔍 Abriendo reporte..."
    allure open allure-report
else
    echo "❌ No se encontraron resultados de prueba. Verifica errores en la ejecución."
    exit 1
fi
