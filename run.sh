#!/usr/bin/env bash
set -e

rm -rf build
mkdir -p build

# Tüm .java dosyalarını bul, javac'a ver
javac -encoding UTF-8 -d build $(find src/main/java -name "*.java")

java -cp build app.Main "$@"


# ./run.sh simple
# ./run.sh communes