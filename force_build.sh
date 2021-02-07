#!/bin/sh

# IF YOU ARE RUNNING INTO ERRORS, THIS SCRIPT WILL RUN THROUGH 
# ALL THE STEPS NEEDED TO BUILD CORRECTLY, IF THIS DOES NOT WORK 
# THERE IS SOMETHING ELSE WRONG WITH YOUR PROJECT.

echo ""
echo "+------------------------+"
echo "| Formatting Document... |"
echo "+------------------------+"

# Apply formatter
#   - The build will not pass if the project is not formatted
./gradlew spotlessApply

echo ""
echo "+----------------------+"
echo "| Building Javadocs... |"
echo "+----------------------+"

# Build Javadocs
#   - This is a nice thing to do when building
./gradlew javadoc

echo ""
echo "+----------------------+"
echo "| Building Jar File... |"
echo "+----------------------+"

# Build Jar
./gradlew build