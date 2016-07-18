package com.mercateo.spring.boot.starter.hornetq.clustered;

import javax.jms.ConnectionFactory;

import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.hornetq.ClusteredHornetQConnectionFactoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NonNull;

/**
 * Provides HornetQConnectionFactory bean.
 * 
 * @author CFM
 *
 */
@Configuration
@ConditionalOnMissingBean(ConnectionFactory.class)
public class ClusteredHornetQConnectionFactoryConfiguration {
    @Bean
    public HornetQConnectionFactory jmsConnectionFactory(@NonNull ListableBeanFactory beanFactory,
            @NonNull ClusteredHornetQProperties properties) {
        return new ClusteredHornetQConnectionFactoryFactory(beanFactory, properties)
                .createConnectionFactory(HornetQConnectionFactory.class);
    }
}
