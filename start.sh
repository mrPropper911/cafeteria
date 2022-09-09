#!/bin/bash

# Prepare Jar
mvn -f cafiteria_back/ clean package

# Ensure, that docker-compose stopped
docker-compose down

# Start new deployment
docker-compose up --build -d

