FROM java:8-jdk
VOLUME target
ADD service_start.sh service_start.sh
RUN chmod +x service_start.sh
CMD bash -c '/service_start.sh'
