package com.mercateo.spring.boot.starter.hornetq.cluster;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQConnectionFactory;

import com.google.common.collect.Iterables;

import lombok.NonNull;

/**
 * Provides factory methods to create {@link HornetQConnectionFactory} instances from
 * multiple authorities.
 * 
 * @author CFM
 */
public class HornetQConnectionFactories {
    public static <T extends HornetQConnectionFactory> T fromAuthorities(
            @NonNull Class<T> factoryClass, @NonNull String authorities) throws Exception {
        List<TransportConfiguration> configs = Arrays.stream(authorities.split(",")).map(
                String::toLowerCase).map(String::trim).map(TransportConfigurations::fromAuthority)
                .collect(Collectors.toList());

        Constructor<T> constructor = factoryClass.getConstructor(boolean.class,
                TransportConfiguration[].class);
        return constructor.newInstance(true, Iterables.toArray(configs,
                TransportConfiguration.class));
    }
}
