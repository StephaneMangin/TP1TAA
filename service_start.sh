#!/bin/sh

# Wait for the postgres container to be available
until nc -z $(docker-compose ps | grep postgres | awk '{print $4}') Up
do
    echo "waiting for postgres container..."
    sleep 2
done

java -Dspring.datasource.url "jdbc:mysql://$MYSQL_PORT_3306_TCP_ADDR:$MYSQL_PORT_3306_TCP_PORT/mysql" -jar /totoapp-0.0.1-SNAPSHOT.jar

