package com.sharathp.service.symptom_management.config;

import com.sharathp.service.symptom_management.auth.OAuthSmUserDetailsService;
import com.sharathp.service.symptom_management.model.Role;
import com.sharathp.service.symptom_management.model.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import java.util.Arrays;

@Configuration
public class OAuth2SecurityConfiguration {

    /**
     *	Resource Server Configuration
     */
    @Configuration
    @EnableResourceServer
    @Order(4)
    protected static class ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/patients/**")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("DOCTOR");
        }
    }

    /**
     * This class is used to configure how our authorization server (the "/oauth/token" endpoint)
     * validates client credentials.
     */
    @Configuration
    @EnableAuthorizationServer
    @Order(Ordered.LOWEST_PRECEDENCE - 100)
    protected static class OAuth2Config extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("mobile_doctor").authorizedGrantTypes("password","refresh_token")
                    .authorities(Role.DOCTOR_CLIENT_ROLE)
                    .scopes(Scope.READ.name(), Scope.WRITE.name())
                    .secret("mobile_doctor")
                    .and()
                    .withClient("mobile_patient").authorizedGrantTypes("password", "refresh_token")
                    .authorities(Role.PATIENT_CLIENT_ROLE)
                    .scopes(Scope.READ.name(), Scope.WRITE.name())
                    .secret("mobile_patient")
            ;
        }

        /**
         * This method tells our AuthorizationServerConfigurerAdapter to use the delegated AuthenticationManager
         * to process authentication requests.
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager());
        }

        // this authentication manager exists only to wrap the userDetailsService with OAuthSmUserDetailsService..
        private AuthenticationManager authenticationManager() {
            final UserDetailsService wrappedUserDetailsService = new OAuthSmUserDetailsService(userDetailsService);
            final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(wrappedUserDetailsService);
            final AuthenticationManager authenticationManager = new ProviderManager(Arrays.asList(authenticationProvider));
            return authenticationManager;
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Bean
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }
    }
}