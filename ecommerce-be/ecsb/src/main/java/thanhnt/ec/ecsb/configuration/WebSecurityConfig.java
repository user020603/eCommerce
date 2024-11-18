package thanhnt.ec.ecsb.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import thanhnt.ec.ecsb.filters.JwtTokenFilter;
import thanhnt.ec.ecsb.model.Role;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.
                            requestMatchers(
                            String.format("%s/users/register", apiPrefix),
                            String.format("%s/users/login", apiPrefix),
                            String.format("%s/healthcheck/**", apiPrefix)
                    )
                            .permitAll()
                            .requestMatchers(GET, String.format("%s/roles**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/products/**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/products/images/*", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/categories/**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/orders/**", apiPrefix)).permitAll()
                            .requestMatchers(GET, String.format("%s/order_details/**", apiPrefix)).permitAll()
                            .anyRequest().authenticated();

                })
                .csrf(AbstractHttpConfigurer::disable);
        http.securityMatcher(String.valueOf(EndpointRequest.toAnyEndpoint()));
        return http.build();
    }
}
