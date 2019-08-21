package com.rain.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rain
 *
 * Configuration table prefixes, which are filtered when entities are generated
 */
@Configuration
@ConfigurationProperties(prefix = "table")
public class PrefixConfig {

    private String prefix;

    public String getPre() {
        return prefix;
    }

    public void setPre(String pre) {
        this.prefix = pre;
    }

    public Integer getPrefixLength(){
        return prefix.equals("null")?prefix.length():0;
    }
}
