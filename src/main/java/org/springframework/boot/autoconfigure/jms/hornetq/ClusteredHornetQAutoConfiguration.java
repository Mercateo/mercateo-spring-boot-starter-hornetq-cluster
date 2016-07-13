package org.springframework.boot.autoconfigure.jms.hornetq;

import javax.jms.ConnectionFactory;

import org.hornetq.api.jms.HornetQJMSClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Slightly altered version of the original {@link HornetQAutoConfiguration}
 * configuration class integrating custom factories and configuration classes
 * for connecting to HornetQ cluster.
 * 
 * @author CFM
 */
@Configuration
@AutoConfigureBefore({ JmsAutoConfiguration.class, HornetQAutoConfiguration.class })
@AutoConfigureAfter({ JndiConnectionFactoryAutoConfiguration.class })
@ConditionalOnClass({ ConnectionFactory.class, HornetQJMSClient.class })
@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties(ClusteredHornetQProperties.class)
@Import({ HornetQEmbeddedServerConfiguration.class,
        /* HornetQXAConnectionFactoryConfiguration.class, */
        ClusteredHornetQConnectionFactoryConfiguration.class,
        UserCredentialsConnectionFactoryAdapterConfiguration.class })
public class ClusteredHornetQAutoConfiguration {
}
