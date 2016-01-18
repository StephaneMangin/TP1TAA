FROM java:8-jdk
VOLUME target
RUN java -Dspring.datasource.url "jdbc:mysql://$MYSQL_PORT_3306_TCP_ADDR:$MYSQL_PORT_3306_TCP_PORT/mysql" -jar /org.istic.taa.totoapp-0.0.1-SNAPSHOT.jar
