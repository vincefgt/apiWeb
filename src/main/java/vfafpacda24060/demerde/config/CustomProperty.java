package vfafpacda24060.demerde.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api") // mapper le namespace
@Data
public class CustomProperty {
    private String url;

    public String getApiURL() {
        return url;
    }
}