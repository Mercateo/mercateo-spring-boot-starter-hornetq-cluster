package com.mercateo.spring.boot.starter.hornetq.cluster;

import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jms.hornetq.ClusteredHornetQConnectionFactoryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class providing bean for
 * {@link UserCredentialsConnectionFactoryAdapter} wrapping a
 * {@link HornetQConnectionFactory} instance, applying the given user
 * credentials to every standard <code>createConnection()</code> call.
 * 
 * @author CFM
 */
@ConditionalOnProperty("spring.hornetq.user")
@Slf4j
@Configuration
public class UserCredentialsConnectionFactoryAdapterConfiguration {

    @Primary
    @Bean
    UserCredentialsConnectionFactoryAdapter jmsConnectionFactory(
            @NonNull ListableBeanFactory beanFactory,
            @NonNull ClusteredHornetQProperties properties) {
        log.info("creating user credentials wrapper for available HornetQConnectionFactory");
        UserCredentialsConnectionFactoryAdapter secureFactory = new UserCredentialsConnectionFactoryAdapter();
        secureFactory.setUsername(properties.getUser());
        secureFactory.setPassword(properties.getPassword());
        secureFactory.setTargetConnectionFactory(new ClusteredHornetQConnectionFactoryFactory(
                beanFactory, properties).createConnectionFactory(HornetQConnectionFactory.class));
        return secureFactory;
    }
}