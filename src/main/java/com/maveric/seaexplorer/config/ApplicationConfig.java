package com.maveric.seaexplorer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US); // Default to US English
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages"); // base name of the resource bundle
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("John")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("UPDATE_PROBE_MOVES", "UPDATE_SEA_OBSTACLES", "VIEW_PROBE", "VIEW_SEA_OBSTACLES")
                .build();
        UserDetails admin = User.builder()
                .username("Mathew")
                .password(bCryptPasswordEncoder().encode("1234"))
                .roles("VIEW_PROBE", "VIEW_SEA_OBSTACLES")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
