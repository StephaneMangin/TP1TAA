#!/bin/sh

# Wait for the postgres container to be available
result=$(nc -vnz "$POSTGRES_PORT_5432_TCP_ADDR" "$POSTGRES_PORT_5432_TCP_PORT" 2>&1 | cut -d' ' -f7)

while [ "$result" != "succeeded!" ]; do
    echo "waiting for postgres container..."
    sleep 2
done

echo "Starting service."
java -Dspring.datasource.url="jdbc:postgresql://$POSTGRES_PORT_5432_TCP_ADDR:$POSTGRES_PORT_5432_TCP_PORT/jhipster" -jar todoapp-0.0.1-SNAPSHOT.jar

