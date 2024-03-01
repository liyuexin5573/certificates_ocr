package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token")
public class AuthConfig {
    private static String clientId;
    private static String clientSecret;
    private static String URL;

    public static String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        AuthConfig.clientId = clientId;
    }

    public static String getClientSecret()
    {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret)
    {
        AuthConfig.clientSecret = clientSecret;
    }

    public static String getUrl() {
        return URL;
    }
    public static void setUrl(String URL) {
        AuthConfig.URL = URL;
    }
}
