FROM landoop/fast-data-dev:latest

ENV ADV_HOST=127.0.0.1

EXPOSE 2181
EXPOSE 8081
EXPOSE 8082
EXPOSE 8083
EXPOSE 9092
EXPOSE 3030

#docker run --rm -it  -p 2181:2181 -p 3030:3030 -p 8081:8081 -p 8082:8082 -p 8083:8083 -p 9092:9092 -e ADV_HOST=192.168.99.100 -name kafka-dev landoop/fast-data-dev
# kafka-topics --create --topic first-topic --zookeeper 192.168.99.100:2181 --replication-factor 1 --partitions 3