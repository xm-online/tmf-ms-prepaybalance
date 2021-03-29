package com.icthh.xm.tmf.ms.prepaybalance.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.CompositeHealthIndicator;
import org.springframework.boot.actuate.health.DefaultHealthIndicatorRegistry;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.OrderedHealthAggregator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Configuration
public class CustomHealthIndicator {

    @Bean
    public CompositeHealthIndicator bindersHealthIndicator() {
        return new CompositeHealthIndicator(new OrderedHealthAggregator(),
            new DefaultHealthIndicatorRegistry());
    }

}
