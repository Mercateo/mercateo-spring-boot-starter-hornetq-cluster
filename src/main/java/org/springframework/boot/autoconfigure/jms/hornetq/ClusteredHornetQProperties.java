package org.springframework.boot.autoconfigure.jms.hornetq;

import org.springframework.boot.autoconfigure.jms.hornetq.HornetQProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author CFM
 *
 */
@ConfigurationProperties(prefix = "spring.hornetq")
public class ClusteredHornetQProperties extends HornetQProperties {
    private String authorities;

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
