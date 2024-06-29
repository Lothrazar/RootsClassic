#!/bin/bash

echo 'deploying...'

./gradlew cleanJar build signJar

echo 'jar deployed to ./build/libs/'
