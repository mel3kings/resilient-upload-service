package resilient.service


import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.InitializingBean
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory


class UploadService implements InitializingBean {
    def topicName = 'first-topic'
    def kafkaServer = '192.168.99.100'
    def producerProperties = [:], consumerProperties = [:]
    def kafkaProducer

    void afterPropertiesSet() {
        producerProperties["bootstrap.servers"] = kafkaServer + ':9092'
        producerProperties["serializer.class"] = 'kafka.serializer.DefaultEncoder'
        producerProperties["key.serializer"] = 'org.apache.kafka.common.serialization.StringSerializer'
        producerProperties["value.serializer"] = 'org.apache.kafka.common.serialization.StringSerializer'
        kafkaProducer = new KafkaProducer(producerProperties)

        consumerProperties.put("bootstrap.servers", kafkaServer + ":9092")

        consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        consumerProperties.put("group.id", topicName)
     //   consumerProperties.put("partition.assignment.strategy", "roundrobin")
    }

    def send(message) {
        ProducerRecord record = new ProducerRecord(topicName, message.toString())
        kafkaProducer.send(record)
    }

    def load() {
        def defaultFactory = new DefaultKafkaConsumerFactory(consumerProperties, new StringDeserializer(), new StringDeserializer())
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(defaultFactory)

        def  consumer= factory.getConsumerFactory().createConsumer()
        consumer.subscribe(Arrays.asList(topicName))
        while (true) {
            def consumerRecords = consumer.poll(3000)
            for(item in consumerRecords){
                println(item)
                println("VALUE: " + item.value())
            }
            Thread.sleep(3000)
            System.out.println("iterating..")

        }
    }
}
