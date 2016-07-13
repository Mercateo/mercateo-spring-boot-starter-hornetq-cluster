/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.jms.hornetq;

import javax.jms.ConnectionFactory;

import org.hornetq.api.jms.HornetQJMSClient;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration Auto-configuration} to integrate with an
 * HornetQ broker. If the necessary classes are present, embed the broker in the
 * application by default. Otherwise, connect to a broker available on the local
 * machine with the default settings.
 *
 * @author Stephane Nicoll
 * @author Phillip Webb
 * @since 1.1.0
 * @see HornetQProperties
 */
@Configuration
@AutoConfigureBefore({ JmsAutoConfiguration.class, HornetQAutoConfiguration.class })
@AutoConfigureAfter({ JndiConnectionFactoryAutoConfiguration.class })
@ConditionalOnClass({ ConnectionFactory.class, HornetQJMSClient.class })
@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties(ClusteredHornetQProperties.class)
@Import({ HornetQEmbeddedServerConfiguration.class,
        /* HornetQXAConnectionFactoryConfiguration.class, */
        ClusteredHornetQConnectionFactoryConfiguration.class })
public class ClusteredHornetQAutoConfiguration {

}
