call mvn clean test

call mvn package -P windows -DskipTests 
call mvn package -P linux -DskipTests