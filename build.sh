#!/bin/bash

# compile and test only once for current platform
mvn clean test

if [[ $? == 0 ]] ; then

    # create packages only if all tests are ok
    for platform in windows linux; do
        mvn package -P $platform -DskipTests
    done

fi

