#!/usr/bin/env bash

set -e

echo "Cleaning previous build..."
rm -rf build

echo "Creating build directory..."
mkdir -p build

echo "Compiling sources..."
javac -d build src/main/java/arbreb/*.java

echo "Running application..."
java -cp build arbreb.ArbreB
