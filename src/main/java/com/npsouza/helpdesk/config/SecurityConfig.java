package com.npsouza.helpdesk.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.npsouza.helpdesk.security.JWTAuthenticationFilter;
import com.npsouza.helpdesk.security.JWTAuthorizationFilter;
import com.npsouza.helpdesk.security.JWTUtil;

//
//public class SecurityConfig extends WebSecurityConfigurerAdapter - desta forma não funciona mais

//@EnableGlobalMethodSecurity(prePostEnabled = true) // esta deprecado no lugar esta sendo usado EnableMethodSecurity
//@EnableMethodSecurity
@Configuration
public class SecurityConfig{
	
	//@Autowired
	//private Environment env;
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService;
	

	//Para testar na linha 46
	//@Autowired
	//private AuthenticationConfiguration authenticationConfiguration;
	

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .anyRequest().authenticated()
                )
                .addFilter(new JWTAuthenticationFilter(authenticationManager, jwtUtil))
                .addFilter(new JWTAuthorizationFilter(authenticationManager, jwtUtil, userDetailsService))
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
//    	if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
//    		http.headers().frameOptions().disable();
//    	}
//    	
//        http
//            .csrf().disable()
//            
//            //Testando este metodo abaixo desta forma para ver se funciona
//            .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtUtil))
//            
//            .authorizeRequests(authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/h2-console/**").permitAll()
//                    .anyRequest().authenticated()
//            )
//            .httpBasic(withDefaults -> {})
//            .sessionManagement(sessionManagement -> sessionManagement
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            )
//            .cors(withDefaults -> {});
//
//        return http.build();
    }
    
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/h2-console/**")
        );
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
    	configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    	UrlBasedCorsConfigurationSource urlSource = new UrlBasedCorsConfigurationSource();
    	urlSource.registerCorsConfiguration("/**", configuration);
    	return urlSource; //Se não fizer este cast da erro
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}
