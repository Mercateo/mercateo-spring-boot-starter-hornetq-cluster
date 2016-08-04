package org.springframework.boot.autoconfigure.jms.hornetq;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.google.common.annotations.VisibleForTesting;
import com.mercateo.spring.boot.starter.hornetq.cluster.ClusteredHornetQProperties;
import com.mercateo.spring.boot.starter.hornetq.cluster.HornetQConnectionFactories;

/**
 * Factory able to create a factory that creates {@link TransportConfiguration}s
 * to multiple HornetQ hosts in a cluster.
 * 
 * Defaults to standard {@link HornetQConnectionFactoryFactory} behavior if
 * <code>spring.hornetq.authorities</code> is not defined.
 * 
 * @author CFM
 *
 */
@Slf4j
public class ClusteredHornetQConnectionFactoryFactory extends HornetQConnectionFactoryFactory {

    private final String authorities;

    private final Integer windowSize;

    public ClusteredHornetQConnectionFactoryFactory(@NonNull ListableBeanFactory beanFactory,
            @NonNull ClusteredHornetQProperties properties) {
        super(beanFactory, properties);
        authorities = properties.getAuthorities();
        windowSize = properties.getWindowSize();
    }

    @Override
    public <T extends HornetQConnectionFactory> T createConnectionFactory(
            @NonNull Class<T> factoryClass) {

        T connectionFactory;

        if (authorities == null) {
            connectionFactory = callSuperCreateConnectionFactory(factoryClass);
        } else {
            try {
                log.info("creating native connection factory from spring.hornetq.authorities");
                connectionFactory = HornetQConnectionFactories.fromAuthorities(factoryClass,
                        authorities);
            } catch (Exception e) {
                throw new IllegalStateException("Unable to create HornetQConnectionFactory", e);
            }
        }

        if (windowSize != null) {
            connectionFactory.setConsumerWindowSize(windowSize);
        }

        return connectionFactory;
    }

    @VisibleForTesting
    <T extends HornetQConnectionFactory> T callSuperCreateConnectionFactory(
            @NonNull Class<T> factoryClass) {
        log.info("spring.hornetq.authorities not set - back to superclass behavior");
        return super.createConnectionFactory(factoryClass);
    }
}
