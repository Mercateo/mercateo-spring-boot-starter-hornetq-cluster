package org.springframework.boot.autoconfigure.jms.hornetq;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import javax.jms.ConnectionFactory;

import org.hornetq.jms.client.HornetQConnectionFactory;
import org.junit.After;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

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

        ConnectionFactory viaType = ctx.getBean(ConnectionFactory.class);
        assertSame(jmsConnectionFactory, viaType);
    }

    @Test
    public void shouldUseAutoconfigurationToCreateMissingBeanWithCredentials() {
        ctx = new AnnotationConfigApplicationContext();
        Properties properties = new Properties();
        properties.setProperty("spring.hornetq.user", "user");
        ctx.getEnvironment().getPropertySources().addFirst(new PropertiesPropertySource(
                "credentials", properties));
        ctx.register(ClusteredHornetQAutoConfiguration.class);
        ctx.refresh();
        UserCredentialsConnectionFactoryAdapter jmsConnectionFactory = (UserCredentialsConnectionFactoryAdapter) ctx
                .getBean("jmsConnectionFactory");
        assertNotNull(jmsConnectionFactory);

        ConnectionFactory viaType = ctx.getBean(ConnectionFactory.class);
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

    @After
    public void tearDown() {
        try {
            ctx.close();
        } catch (Exception e) {
            // can't help it then...
        }
    }
}