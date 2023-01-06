#!/usr/bin/env bash

# compile
./gradlew compileJava

# build
./gradlew build

# run test cases
./gradlew test

# package
./gradlew assemble

# run
./gradlew bootRun