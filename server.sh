#!/bin/bash

# Kill any existing process on port 8025
lsof -ti:8025 | xargs kill -9 2>/dev/null || true

# Rebuild the project
mvn clean package

# Start the server in the background
java -jar target/uno-server.jar &
SERVER_PID=$!