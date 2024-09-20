#!/bin/bash

# create packages for all platforms only if all tests are ok
for platform in linux64 macArm64 macX86 win64; do
    mvn -DskipTests=true package -P $platform
done


