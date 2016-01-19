FROM java:8-jdk

VOLUME /tmp

ADD service_start.sh service_start.sh
ADD target/todoapp-0.0.1-SNAPSHOT.jar todoapp-0.0.1-SNAPSHOT.jar

RUN chmod +x service_start.sh
RUN apt-get update && apt-get install -y netcat-openbsd
CMD ./service_start.sh
