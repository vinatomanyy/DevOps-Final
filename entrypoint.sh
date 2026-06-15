#!/bin/bash

service ssh start
nginx -g "daemon off;" &

until mysqladmin ping -h db -u root -pHello@123 --silent; do
    echo "Waiting for MySQL..."
    sleep 3
done

java -jar /app/target/*.jar
