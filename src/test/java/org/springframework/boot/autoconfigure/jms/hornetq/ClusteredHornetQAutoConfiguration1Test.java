package org.springframework.boot.autoconfigure.jms.hornetq;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import javax.jms.ConnectionFactory;

import org.hornetq.jms.client.HornetQConnectionFactory;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class ClusteredHornetQAutoConfiguration1Test {

    private AnnotationConfigApplicationContext ctx;

    @Test
    public void shouldUseAutoconfigurationToCreateMissingBean() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(ClusteredHornetQAutoConfiguration.class);
        ctx.refresh();
        HornetQConnectionFactory jmsConnectionFactory = (HornetQConnectionFactory) ctx.getBean(
                "jmsConnectionFactory");
        assertNotNull(jmsConnectionFactory);

        HornetQConnectionFactory viaType = ctx.getBean(HornetQConnectionFactory.class);
        assertSame(jmsConnectionFactory, viaType);
    }

    static class UserProvidedFactory {

        static final HornetQConnectionFactory dummyFactory = mock(HornetQConnectionFactory.class);

        @Bean
        ConnectionFactory jmsConnectionFactory() {
            return dummyFactory;
        }
    }

    @Test
    public void shouldUseUserProvidedConfigOverAutoconfiguration() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(UserProvidedFactory.class, ClusteredHornetQAutoConfiguration.class);
        ctx.refresh();
        HornetQConnectionFactory jmsConnectionFactory = (HornetQConnectionFactory) ctx.getBean(
                "jmsConnectionFactory");
        assertSame(UserProvidedFactory.dummyFactory, jmsConnectionFactory);

        HornetQConnectionFactory viaType = ctx.getBean(HornetQConnectionFactory.class);
        assertSame(UserProvidedFactory.dummyFactory, viaType);
    }

    static class UserProvidedFactory2 {

        static final HornetQConnectionFactory dummyFactory = mock(HornetQConnectionFactory.class);

        @Bean
        HornetQConnectionFactory jmsConnectionFactory() {
            return dummyFactory;
        }
    }

    @Test
    public void shouldUseUserProvidedConfigOverAutoconfiguration2() {
        ctx = new AnnotationConfigApplicationContext();
        ctx.register(UserProvidedFactory2.class, ClusteredHornetQAutoConfiguration.class);
        ctx.refresh();
        HornetQConnectionFactory jmsConnectionFactory = (HornetQConnectionFactory) ctx.getBean(
                "jmsConnectionFactory");
        assertSame(UserProvidedFactory2.dummyFactory, jmsConnectionFactory);

        HornetQConnectionFactory viaType = ctx.getBean(HornetQConnectionFactory.class);
        assertSame(UserProvidedFactory2.dummyFactory, viaType);
    }

    @After
    public void tearDown() {
        try {
            ctx.close();
        } catch (Exception e) {
            // can't help it then...
        }
    }
}