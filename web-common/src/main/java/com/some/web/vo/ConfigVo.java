package com.some.web.vo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "config.self")
@Component
@Data
public class ConfigVo {
    private String ak;
    private String logLevel;
    private String activeProfile;

}
