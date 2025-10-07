#!/bin/bash

echo "=================="
echo "Maven 安裝依賴與編譯"
echo "=================="
mvn clean install > run.log

echo "=================="
echo "Maven 執行"
echo "=================="
mvn exec:java >> run.log
