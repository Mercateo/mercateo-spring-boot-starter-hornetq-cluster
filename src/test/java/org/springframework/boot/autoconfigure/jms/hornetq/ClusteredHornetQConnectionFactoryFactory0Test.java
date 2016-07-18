package org.springframework.boot.autoconfigure.jms.hornetq;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.jms.client.HornetQConnectionFactory;
import org.junit.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.jms.hornetq.ClusteredHornetQConnectionFactoryFactory;

import com.mercateo.spring.boot.starter.hornetq.clustered.ClusteredHornetQProperties;

public class ClusteredHornetQConnectionFactoryFactory0Test {

    private ListableBeanFactory mockBeanFactory = mock(ListableBeanFactory.class);

    private Class<? extends HornetQConnectionFactory> factoryClass = HornetQConnectionFactory.class;

    @Test(expected = IllegalArgumentException.class) // Nope that's not an NPE
    public void mustBlowUpOnNullBeanFactory() throws Exception {
        new ClusteredHornetQConnectionFactoryFactory(null, new ClusteredHornetQProperties());
    }

    @Test(expected = IllegalArgumentException.class) // Nope that's not an NPE
    public void mustBlowUpOnNullProperties() throws Exception {
        new ClusteredHornetQConnectionFactoryFactory(mockBeanFactory, null);
    }

    @Test(expected = NullPointerException.class)
    public void mustBlowUpOnNullFactoryClass() throws Exception {
        ClusteredHornetQProperties noAuthorities = new ClusteredHornetQProperties();
        ClusteredHornetQConnectionFactoryFactory uut = new ClusteredHornetQConnectionFactoryFactory(
                mockBeanFactory, noAuthorities);
        uut.createConnectionFactory(null);
    }

    @Test
    public void mustFallbacktoSuperclassBecauseNoAuthoritiesConfigured() throws Exception {
        ClusteredHornetQProperties noAuthorities = new ClusteredHornetQProperties();
        ClusteredHornetQConnectionFactoryFactory uut = spy(
                new ClusteredHornetQConnectionFactoryFactory(mockBeanFactory, noAuthorities));
        uut.createConnectionFactory(factoryClass);
        verify(uut, times(1)).callSuperCreateConnectionFactory(factoryClass);
    }

    @Test
    public void mustNotFallbacktoSuperclassBecauseTwoAuthoritiesConfigured() throws Exception {
        ClusteredHornetQProperties twoAuthorities = new ClusteredHornetQProperties();
        twoAuthorities.setAuthorities("foo:8888,bar:9999");
        ClusteredHornetQConnectionFactoryFactory uut = spy(
                new ClusteredHornetQConnectionFactoryFactory(mockBeanFactory, twoAuthorities));
        uut.createConnectionFactory(factoryClass);
        verify(uut, never()).callSuperCreateConnectionFactory(factoryClass);
    }

    @Test
    public void mustCreateTransportConfigurationsAccordingToAuthorities() throws Exception {
        ClusteredHornetQProperties twoAuthorities = new ClusteredHornetQProperties();
        twoAuthorities.setAuthorities("foo:8888,bar:9999");
        ClusteredHornetQConnectionFactoryFactory uut = spy(
                new ClusteredHornetQConnectionFactoryFactory(mockBeanFactory, twoAuthorities));
        HornetQConnectionFactory connectionFactory = uut.createConnectionFactory(factoryClass);
        TransportConfiguration[] staticConnectors = connectionFactory.getStaticConnectors();
        assertEquals(2, staticConnectors.length);
        assertEquals("foo", staticConnectors[0].getParams().get("host"));
        assertEquals("8888", staticConnectors[0].getParams().get("port"));
        assertEquals("bar", staticConnectors[1].getParams().get("host"));
        assertEquals("9999", staticConnectors[1].getParams().get("port"));
    }
}
