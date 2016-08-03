package com.mercateo.spring.boot.starter.hornetq.cluster;

import org.springframework.boot.autoconfigure.jms.hornetq.HornetQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Property class extending {@link HornetQProperties} to also include
 * <code>spring.hornetq.authorities</code>, <code>spring.hornetq.user</code> and
 * <code>spring.hornetq.password</code>.
 * 
 * @author CFM
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(prefix = "spring.hornetq")
public class ClusteredHornetQProperties extends HornetQProperties {
    private String authorities;

    private String user;

    private String password;

    private Integer windowSize;
}
