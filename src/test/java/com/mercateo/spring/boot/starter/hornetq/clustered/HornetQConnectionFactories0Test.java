package com.mercateo.spring.boot.starter.hornetq.clustered;

import static org.junit.Assert.*;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.junit.Test;
import com.mercateo.spring.boot.starter.hornetq.clustered.HornetQConnectionFactories;

public class HornetQConnectionFactories0Test {

    @Test(expected = NullPointerException.class)
    public void mustExplodeOnNullFactoryClass() throws Exception {
        HornetQConnectionFactories.fromAuthorities(null, "somehost:12345");
    }

    @Test(expected = NullPointerException.class)
    public void mustExplodeOnNullAuthority() throws Exception {
        HornetQConnectionFactories.fromAuthorities(HornetQConnectionFactory.class, null);
    }

    @Test
    public void mustContainTransportConfigurationToOneGivenAuthority() throws Exception {
        HornetQConnectionFactory connectionFactory = HornetQConnectionFactories.fromAuthorities(
                HornetQConnectionFactory.class, "somehost:12345");
        TransportConfiguration[] staticConnectors = connectionFactory.getStaticConnectors();
        assertEquals(1, staticConnectors.length);
        assertEquals("somehost", staticConnectors[0].getParams().get("host"));
        assertEquals("12345", staticConnectors[0].getParams().get("port"));
    }

    @Test
    public void mustContainTransportConfigurationToTwoGivenAuthorities() throws Exception {
        HornetQConnectionFactory connectionFactory = HornetQConnectionFactories.fromAuthorities(
                HornetQConnectionFactory.class, "foo:8888,bar:9999");
        TransportConfiguration[] staticConnectors = connectionFactory.getStaticConnectors();
        assertEquals(2, staticConnectors.length);
        assertEquals("foo", staticConnectors[0].getParams().get("host"));
        assertEquals("8888", staticConnectors[0].getParams().get("port"));
        assertEquals("bar", staticConnectors[1].getParams().get("host"));
        assertEquals("9999", staticConnectors[1].getParams().get("port"));
    }
}
