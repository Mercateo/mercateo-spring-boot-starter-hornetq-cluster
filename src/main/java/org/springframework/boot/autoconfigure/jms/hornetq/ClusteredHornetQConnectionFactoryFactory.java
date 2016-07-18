package org.springframework.boot.autoconfigure.jms.hornetq;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.jms.hornetq.HornetQConnectionFactoryFactory;

import com.google.common.annotations.VisibleForTesting;
import com.mercateo.spring.boot.starter.hornetq.clustered.ClusteredHornetQProperties;
import com.mercateo.spring.boot.starter.hornetq.clustered.HornetQConnectionFactories;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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

    public ClusteredHornetQConnectionFactoryFactory(@NonNull ListableBeanFactory beanFactory,
            @NonNull ClusteredHornetQProperties properties) {
        super(beanFactory, properties);
        authorities = properties.getAuthorities();
    }

    @Override
    public <T extends HornetQConnectionFactory> T createConnectionFactory(
            @NonNull Class<T> factoryClass) {
        if (authorities == null) {
            return callSuperCreateConnectionFactory(factoryClass);
        }
        try {
            log.info("creating native connection factory from spring.hornetq.authorities");
            return HornetQConnectionFactories.fromAuthorities(factoryClass, authorities);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create HornetQConnectionFactory", e);
        }
    }

    @VisibleForTesting
    <T extends HornetQConnectionFactory> T callSuperCreateConnectionFactory(
            @NonNull Class<T> factoryClass) {
        log.info("spring.hornetq.authorities not set - back to superclass behavior");
        return super.createConnectionFactory(factoryClass);
    }
}
