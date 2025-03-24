package com.maveric.seaexplorer;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BaseControllerTest {

    @BeforeAll
    protected static void setSecurityContextWithAllRoles(){
        Collection<GrantedAuthority> authorities = Arrays.asList(
                    "UPDATE_PROBE_MOVES", "UPDATE_SEA_OBSTACLES", "VIEW_PROBE", "VIEW_SEA_OBSTACLES", "VIEW_PROBE", "VIEW_SEA_OBSTACLES"
                ).stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
        SecurityContext context =  SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken("TEST", "TEST", authorities));
        SecurityContextHolder.setContext(context);
    }
}
