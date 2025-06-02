package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer implements Runnable {
    private final Logger log = LoggerFactory.getLogger(Consumer.class);
    private final KafkaConsumer<String, String> consumer;

    public Consumer(String bootstrapServer, String groupId, String topic) {
        consumer = setupKafkaConsumer(bootstrapServer, groupId);
        consumer.subscribe(Arrays.asList(topic));
    }

    public void run() {
        try {
            // poll for new data
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            // This exception is expected when closing a consumer, no error handling required.
            log.info("Wake up exception");
        } catch (Exception e) {
            log.error("Unexpected exception: ", e);
        } finally {
            consumer.close(); // this will also commit the offsets if necessary.
            log.info("Consumer closed successfully");
        }
    }

    public void shutDown() {
        consumer.wakeup();
    }

    private KafkaConsumer<String, String> setupKafkaConsumer(String bootstrapServer, String groupId) {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new KafkaConsumer<>(properties);
    }
}