package com.example.test.controller;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@PropertySource("classpath:/kafka.properties")
@Service
public class MessagingProducer {
    private static Logger logger = LoggerFactory.getLogger(MessagingProducer.class);

    @Value("${bootstrap.servers}")
    private String bootstrapServers;

    @Value("${key.serializer}")
    private String keySerializer;

    @Value("${value.serializer}")
    private String valueSerializer;

    @Value("${linger.ms}")
    private String lingerMs;

    @Value("${request.timeout.ms}")
    private String requestTimeoutMs;

    @Value("${buffer.memory}")
    private String bufferMemory;

    @Value("${batch.size}")
    private String batchSize;

    private Properties kafkaProperties;
    private static KafkaProducer<String, String> producer;

    @PostConstruct
    public void startKafkaProducer() {
        try {
            kafkaProperties = new Properties();
            kafkaProperties.put("bootstrap.servers", bootstrapServers);
            kafkaProperties.put("key.serializer", keySerializer);
            kafkaProperties.put("value.serializer", valueSerializer);
            kafkaProperties.put("acks", "all");
            kafkaProperties.put("retries", "0");
            kafkaProperties.put("batch.size", batchSize);
            kafkaProperties.put("linger.ms", lingerMs);
            kafkaProperties.put("request.timeout.ms", requestTimeoutMs);
            kafkaProperties.put("buffer.memory", bufferMemory);
            producer = new KafkaProducer<>(kafkaProperties);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Callback createCallback() {
        Callback callback = new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception e) {
                if (e != null) {
                    logger.error("Error sending message to kafka", e);
                    e.printStackTrace();
                } else {
                    logger.info("[KafkaProducerService] Message SENT to topic " + metadata.topic());
                }
            }
        };
        return callback;
    }

    /**
     * Generic sending of message to Messaging Queue using topic name
     *
     * @param message
     * @param topicName
     */
    public void sendMessage(String message, String topicName) {
        Callback callback = createCallback();
        try {
            producer.send(new ProducerRecord<String, String>(topicName, message), callback);
        } catch (IllegalStateException ex) {
            logger.error("Kafka Service:: Producer is closed, initializing and retrying again", ex);
            try {
                producer.send(new ProducerRecord<String, String>(topicName, message), callback);
            } catch (Exception e) {
                logger.error("Kafka Service:: ERROR unable to send message!", e);
            }
        }
    }

}
