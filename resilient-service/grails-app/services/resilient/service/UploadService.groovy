package resilient.service

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
class UploadService {
    def topicName = 'first-topic'
    def kafkaServer='localhost'
    def properties = [:]

    def serviceMethod(message) {
        System.out.println("Trying to save message:" + message)
        properties["bootstrap.servers"]=kafkaServer+':9092'
        properties["serializer.class"]='kafka.serializer.DefaultEncoder'
        properties["key.serializer"]='org.apache.kafka.common.serialization.StringSerializer'
        properties["value.serializer"]='org.apache.kafka.common.serialization.StringSerializer'

        KafkaProducer kafkaProducer = new KafkaProducer(properties)
        ProducerRecord record = new ProducerRecord(topicName, message.toString())
        kafkaProducer.send(record);
     }


}
