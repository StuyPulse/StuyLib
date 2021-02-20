#!/bin/bash

BUILD_FILE="./build/libs/StuyLib-1.0.0.jar"
OUTPUT_FILE="./releases/StuyLib-$1.jar"

if [ -z "$1" ]
  then
    echo "Incorrect Usage, no version specified."
    echo "usage: $0 [version]"
else 
    echo "Removing Old Builds..."
    rm -f $BUILD_FILE
    rm -f $OUTPUT_FILE

    echo "Force Building Project..."
    ./force_build.sh

    if test -f $BUILD_FILE; then 
        mkdir ./releases >/dev/null 2>&1
        cp $BUILD_FILE $OUTPUT_FILE >/dev/null 2>&1
        echo ""
        echo "Succesfully Built StuyLib-$1.jar to ./releases/"
    else
        echo ""
        echo "Unable to Build Release Jar File. Please resolve the errors and try again."
    fi
fi