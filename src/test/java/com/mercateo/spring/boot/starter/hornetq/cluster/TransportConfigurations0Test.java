package com.mercateo.spring.boot.starter.hornetq.cluster;

import static org.junit.Assert.assertEquals;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.junit.Test;

import com.mercateo.spring.boot.starter.hornetq.cluster.TransportConfigurations;

public class TransportConfigurations0Test {
    @Test(expected = NullPointerException.class)
    public void fromAuthorityMustExplodeOnNull() throws Exception {
        TransportConfigurations.fromAuthority(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromAuthorityMustExplodeOnMissingPort() throws Exception {
        TransportConfigurations.fromAuthority("host:");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromAuthorityMustExplodeOnMissingHost() throws Exception {
        TransportConfigurations.fromAuthority(":port");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromAuthorityMustExplodeOnMissingPortAndHost() throws Exception {
        TransportConfigurations.fromAuthority(":");
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromAuthorityMustExplodeOnBrokenFormat() throws Exception {
        TransportConfigurations.fromAuthority("host:port:zort");
    }

    @Test
    public void fromAuthorityGoodCase() throws Exception {
        TransportConfiguration transportConfiguration = TransportConfigurations.fromAuthority(
                "somehost:12345");
        assertEquals("somehost", transportConfiguration.getParams().get("host"));
        assertEquals("12345", transportConfiguration.getParams().get("port"));
        assertEquals(NettyConnectorFactory.class.getName(), transportConfiguration
                .getFactoryClassName());
    }
}
