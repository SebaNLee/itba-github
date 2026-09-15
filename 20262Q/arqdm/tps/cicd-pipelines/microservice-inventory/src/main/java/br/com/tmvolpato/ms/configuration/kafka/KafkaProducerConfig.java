package br.com.tmvolpato.ms.configuration.kafka;

import br.com.tmvolpato.ms.adapters.out.message.SaleMessage;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, SaleMessage> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        
        // Basic configurations
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomSerializer.class);
        
        // Reliability configurations
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");  // Strongest durability guarantee
        configProps.put(ProducerConfig.RETRIES_CONFIG, 3);   // Retry on transient errors
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);  // Ensure exactly-once semantics

        // Performance configurations
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);  // Batch size in bytes
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 1);       // Small delay to allow batching
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);  // Total memory for buffering

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, SaleMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}