/**
 * 
 */
package org.springframework.boot.autoconfigure.jms.hornetq;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.Assert;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

/**
 * @author CFM
 *
 */

class ClusteredHornetQConnectionFactoryFactory extends HornetQConnectionFactoryFactory {

    private final String authorities;

    ClusteredHornetQConnectionFactoryFactory(ListableBeanFactory beanFactory,
            ClusteredHornetQProperties properties) {
        super(beanFactory, properties);
        authorities = properties.getAuthorities();
    }

    @Override
    public <T extends HornetQConnectionFactory> T createConnectionFactory(Class<T> factoryClass) {
        if (authorities == null) {
            return super.createConnectionFactory(factoryClass);
        }
        try {
            return createNativeConnectionFactoryFromAuthorities(factoryClass);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to create HornetQConnectionFactory", e);
        }
    }

    private <T extends HornetQConnectionFactory> T createNativeConnectionFactoryFromAuthorities(
            Class<T> factoryClass) throws Exception {
        List<TransportConfiguration> configs = Arrays.stream(authorities.split(",")).map(
                String::toLowerCase).map(String::trim).map(
                        ClusteredHornetQConnectionFactoryFactory::createTransportConfigurationFromAuthority)
                .collect(Collectors.toList());

        Constructor<T> constructor = factoryClass.getConstructor(boolean.class,
                TransportConfiguration[].class);
        return constructor.newInstance(true, Iterables.toArray(configs,
                TransportConfiguration.class));
    }

    private static TransportConfiguration createTransportConfigurationFromAuthority(
            String authority) {
        Assert.notNull(authority, "authority must not be null");
        String[] hostAndPort = authority.split(":");
        Assert.isTrue(hostAndPort.length == 2);
        Assert.isTrue(!hostAndPort[0].isEmpty());
        Assert.isTrue(!hostAndPort[1].isEmpty());
        return new TransportConfiguration(NettyConnectorFactory.class.getName(), ImmutableMap.of(
                TransportConstants.HOST_PROP_NAME, hostAndPort[0],
                TransportConstants.PORT_PROP_NAME, hostAndPort[1]));
    }

}
