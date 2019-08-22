package com.rain.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rain
 *
 * Configuration table prefixes, which are filtered when entities are generated
 */
@Configuration

public class PrefixConfig {

    @Value("${table.prefix}")
    private String prefix;

    public String getPre() {
        return prefix;
    }

    public void setPre(String pre) {
        this.prefix = pre;
    }

    public Integer getPrefixLength(){
       // System.err.println(prefix);
        return "null".equals(prefix)?0:prefix.length();
    }
}
