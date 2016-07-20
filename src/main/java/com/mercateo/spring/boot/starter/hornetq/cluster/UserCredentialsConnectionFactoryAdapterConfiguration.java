package com.mercateo.spring.boot.starter.hornetq.cluster;

import javax.jms.ConnectionFactory;

import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration class proving bean for
 * {@link UserCredentialsConnectionFactoryAdapter} wrapping an available
 * {@link HornetQConnectionFactory} instance, applying the given user
 * credentials to every standard <code>createConnection()</code> call.
 * 
 * @author CFM
 */
@Slf4j
@Configuration
@ConditionalOnBean(ConnectionFactory.class)
public class UserCredentialsConnectionFactoryAdapterConfiguration {
    @Bean
    @Primary
    UserCredentialsConnectionFactoryAdapter createUserCredentialsFactoryAdapter(
            @NonNull HornetQConnectionFactory hornetqFactory,
            @NonNull ClusteredHornetQProperties properties) {
        log.info("creating user credentials wrapper for available HornetQConnectionFactory");
        UserCredentialsConnectionFactoryAdapter secureFactory = new UserCredentialsConnectionFactoryAdapter();
        secureFactory.setUsername(properties.getUser());
        secureFactory.setPassword(properties.getPassword());
        secureFactory.setTargetConnectionFactory(hornetqFactory);
        return secureFactory;
    }
}
