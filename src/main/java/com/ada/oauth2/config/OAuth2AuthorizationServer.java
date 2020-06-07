package com.ada.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Configuration
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private static final Map<String, String> CLIENTS = new HashMap<String, String>() {{
        put("ada-client", "ada-secret");
        put("another-client", "another-secret");
    }};

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder>.ClientBuilder builder =
                clients.inMemory()
                        .withClient("mobile-client")
                        .secret(passwordEncoder().encode("mobile-secret"))
                        .authorizedGrantTypes("client_credentials")
                        .accessTokenValiditySeconds(60)
                        .scopes("resource-server-read", "resource-server-write");

        Iterator<Map.Entry<String, String>> clientEntryIterator = CLIENTS.entrySet().iterator();
        composeBuilder(builder, clientEntryIterator);
    }

    private ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder>.ClientBuilder
    composeBuilder(ClientDetailsServiceBuilder<InMemoryClientDetailsServiceBuilder>.ClientBuilder builder,
                   Iterator<Map.Entry<String, String>> iterator) {
        if (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            builder
                    .and()
                    .withClient(entry.getKey())
                    .secret(passwordEncoder().encode(entry.getValue()))
                    .authorizedGrantTypes("client_credentials")
                    .accessTokenValiditySeconds(60)
                    .scopes("resource-server-read", "resource-server-write");
        }
        if (iterator.hasNext()) {
            return composeBuilder(builder, iterator);
        } else {
            return builder;
        }
    }

//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()")
//                .allowFormAuthenticationForClients();
//    }
}
