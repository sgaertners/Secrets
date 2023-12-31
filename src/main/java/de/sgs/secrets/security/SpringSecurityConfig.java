package de.sgs.secrets.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.*;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SpringSecurityConfig {


    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setCreateSessionAllowed(true);
//        requestCache.setMatchingRequestParameterName(String.valueOf(CacheControl.maxAge(365, TimeUnit.DAYS)));
        requestCache.setMatchingRequestParameterName(null);
        http
//                .csrf(Customizer.withDefaults())
                .csrf().disable()
            .authorizeHttpRequests(authorize ->
                authorize
                .requestMatchers("/user").authenticated()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/roles/**").authenticated()
                .requestMatchers("/addrole/**").authenticated()
                .requestMatchers("/deleterole/**").authenticated()
                .requestMatchers("/rolesfromuser/**").authenticated()
                .requestMatchers("/updateuserroles/**").authenticated()
                .requestMatchers("/welcome/**").authenticated()
                .requestMatchers("/whiskey/**").authenticated()
                .requestMatchers("/knowhow/**").authenticated()
                .requestMatchers("/knowhow/search").authenticated()
                .requestMatchers("/whiskey/save").authenticated()
                .requestMatchers("/whiskey/update").authenticated()
                .requestMatchers("/whiskey/upload").authenticated()
                .requestMatchers("/whiskey/editwhiskey").authenticated()
                .requestMatchers("/whiskey/deletewhiskey").authenticated()
                .requestMatchers("/whiskey/list").authenticated()
                .requestMatchers("/whiskey/print").authenticated()
                .requestMatchers("/dbimage").authenticated()
                .requestMatchers("/js/**").authenticated()
                .requestMatchers("/resources/**").permitAll()
                .requestMatchers("/webjars/**").permitAll()
                .requestMatchers("/").permitAll()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/login/register/**").permitAll()
                .requestMatchers("/failure").authenticated()
                .requestMatchers("/favicon.ico").permitAll()
                .requestMatchers(req -> req.getParameterMap().containsKey("lang")).permitAll() // Spring Security 6 Spring Boot 3 & I18N
                .requestMatchers(req -> req.getParameterMap().containsKey("id")).permitAll()
                .requestMatchers(req -> req.getParameterMap().containsKey("kind")).permitAll()
            ).formLogin(
                form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/welcome")
                        .failureUrl("/failure")
                        .permitAll()
                )
                .logout(
                    logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
            ).requestCache(cache -> cache
                .requestCache(requestCache)
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    final class CsrfCookieFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
            // Render the token value to a cookie by causing the deferred token to be loaded
            csrfToken.getToken();

            filterChain.doFilter(request, response);
        }
    }

 }