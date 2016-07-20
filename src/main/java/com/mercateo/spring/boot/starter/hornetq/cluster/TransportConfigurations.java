package com.mercateo.spring.boot.starter.hornetq.cluster;

import static com.google.common.base.Preconditions.checkArgument;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;

import com.google.common.collect.ImmutableMap;

import lombok.NonNull;

/**
 * Provides factory methods to create {@link TransportConfiguration}s based on
 * authorities: <code>"host:port"</code>.
 * 
 * @author CFM
 */
public class TransportConfigurations {
    public static TransportConfiguration fromAuthority(@NonNull String authority) {
        String[] hostAndPort = authority.split(":");
        checkArgument(hostAndPort.length == 2);
        checkArgument(!hostAndPort[0].isEmpty());
        checkArgument(!hostAndPort[1].isEmpty());
        return new TransportConfiguration(NettyConnectorFactory.class.getName(), ImmutableMap.of(
                TransportConstants.HOST_PROP_NAME, hostAndPort[0],
                TransportConstants.PORT_PROP_NAME, hostAndPort[1]));
    }
}
