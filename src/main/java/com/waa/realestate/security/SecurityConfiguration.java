package com.waa.realestate.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/users" )
                        .hasAnyAuthority("users.read")
                        .requestMatchers(HttpMethod.POST, "/users" )
                        .hasAnyAuthority("users.write")
                        .requestMatchers(HttpMethod.PUT, "/users" )
                        .hasAnyAuthority("users.write")
                        .requestMatchers(HttpMethod.DELETE, "/users" )
                        .hasAnyAuthority("users.delete")

                        .requestMatchers(HttpMethod.GET, "/roles" )
                        .hasAnyAuthority("roles.read")
                        .requestMatchers(HttpMethod.POST, "/roles" )
                        .hasAnyAuthority("roles.write")
                        .requestMatchers(HttpMethod.PUT, "/roles" )
                        .hasAnyAuthority("roles.write")
                        .requestMatchers(HttpMethod.DELETE, "/roles" )
                        .hasAnyAuthority("roles.delete")

                        .requestMatchers(HttpMethod.GET, "/privileges" )
                        .hasAnyAuthority("privileges.read")
                        .requestMatchers(HttpMethod.POST, "/privileges" )
                        .hasAnyAuthority("privileges.write")
                        .requestMatchers(HttpMethod.PUT, "/privileges" )
                        .hasAnyAuthority("privileges.write")
                        .requestMatchers(HttpMethod.DELETE, "/privileges" )
                        .hasAnyAuthority("privileges.delete")

                        .anyRequest().authenticated())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}
