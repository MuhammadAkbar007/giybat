package uz.akbar.giybat.security;

import java.util.Arrays;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** SecurityConfig */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public AuthenticationProvider authenticationProvider() {

    String password = UUID.randomUUID().toString();
    System.out.println("User Password Mazgi: " + password);

    UserDetails user =
        User.builder().username("user").password("{noop}" + password).roles("USER").build();

    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user));
    return authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(
        authorizationManagerRequestMatcherRegistry -> {
          authorizationManagerRequestMatcherRegistry
              .requestMatchers("/auth/**")
              .permitAll()
              .anyRequest()
              .authenticated();
        });

    http.csrf(AbstractHttpConfigurer::disable);

    http.cors(
        httpSecurityCorsConfigurer -> {
          CorsConfiguration configuration = new CorsConfiguration();

          configuration.setAllowedOriginPatterns(Arrays.asList("*"));
          configuration.setAllowedMethods(Arrays.asList("*"));
          configuration.setAllowedHeaders(Arrays.asList("*"));

          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
          source.registerCorsConfiguration("/**", configuration);
          httpSecurityCorsConfigurer.configurationSource(source);
        });

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
