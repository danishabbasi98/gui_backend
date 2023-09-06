//package com.vodacom.er.gui.config;
//
//import com.vodacom.er.gui.filter.JwtFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
////
////    @Autowired
////    private LdapAuthenticationProvider ldapAuthenticationProvider;
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors(cores -> {
//            CorsConfigurationSource corsConfigurationSource = corsConfigurationSource();
//            cores.configurationSource(corsConfigurationSource);
//        })
//                .csrf().disable().authorizeRequests()
//                .antMatchers("/login","/update/**","/h2-console/**"
//                        , "/es/**"
//                        , "/group/**", "/function/**", "/session/**", "/sub/**", "/user/**", "/subscription/**").permitAll()
//                .anyRequest().authenticated()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//}




package com.vodacom.er.gui.config;

import com.vodacom.er.gui.filter.JwtFilter;
import com.vodacom.er.gui.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private LdapAuthenticationProvider ldapAuthenticationProvider;
//
//    //	@Autowired
////    private CustomUserDetailsService customUserDetailsService;
////
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    //	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////		http.csrf().disable()
////		.authorizeRequests().antMatchers(HttpMethod.GET, new String[] { "/css/**", "/js/**", "/images/**", "/", "/login" ,
////				"/authenticate","/h2-console/**","/es/**"
////                ,"/group/**","/function/**","/session/**","/sub/**","/user/**"}).permitAll()
////				.and()
////				.httpBasic();
////	}
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
////    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
////		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
////		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
////		configuration.setExposedHeaders(Arrays.asList("Authorization"));
////		configuration.setAllowCredentials(true);
////		configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors(cors -> {
//            CorsConfigurationSource corsConfigurationSource = corsConfigurationSource();
//            cors.configurationSource(corsConfigurationSource);
//        })
//                .csrf().disable().authorizeRequests()
////			.antMatchers("/login","/authenticate","/h2-console/**"
////					"/es/**"
////					,"/group/**","/function/**","/session/**","/sub/**","/user/**","/subscription/**"
////			).permitAll()
////			.anyRequest().authenticated()
////			.and().httpBasic();
////			.and().csrf().ignoringAntMatchers("/authenticate","/h2-console/**","/es/**"
////                ,"/group/**","/function/**","/session/**","/sub/**","/user/**","/subscription/**")
////            .and().headers().frameOptions().sameOrigin()
//                .antMatchers("/login","/update/**","/h2-console/**"
//                        , "/es/**"
//                        , "/group/**", "/function/**", "/session/**", "/sub/**", "/user/**", "/subscription/**").permitAll()
////                .hasRole("ADMIN")
////                .antMatchers("/login","/update/**"
////                        , "/es/**"
////                        , "/group/**", "/function/**", "/session/**", "/sub/**", "/user/**", "/subscription/**").hasAnyRole("NORMAL")
////                .antMatchers("/login", "/authenticate", "/h2-console/**").permitAll()
////            .and().authorizeRequests().antMatchers("/update/**").hasRole("STAFF")
//                .anyRequest().authenticated()
////            .and().exceptionHandling().accessDeniedPage("/accessDenied.jsp")//for 404 error otherwise its return 403 forbidden error
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
////	@Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////            .csrf().disable().authorizeRequests()
//////            .antMatchers("/authenticate","/h2-console/**")
////        .antMatchers(new String[] { "/css/**", "/js/**", "/images/**", "/", "/login",
////                "/authenticate","/h2-console/**",
////                "/authenticate","/h2-console/**","/es/**"
////                ,"/group/**","/function/**","/session/**","/sub/**","/user/**","/subscription/**"})
////            .permitAll()
//////            .and().csrf().ignoringAntMatchers("/authenticate","/h2-console/**","/es/**"
//////                ,"/group/**","/function/**","/session/**","/sub/**","/user/**")
////            .and().headers().frameOptions().sameOrigin()
////            .and().authorizeRequests().antMatchers("/add").hasAnyRole("ADMIN","STAFF")
////            .and().authorizeRequests().antMatchers("/update/**").hasRole("STAFF")
////            .anyRequest().authenticated()
////            .and().exceptionHandling().accessDeniedPage("/accessDenied.jsp")//for 404 error otherwise its return 403 forbidden error
////            .and().sessionManagement()
////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
////    }
//
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//}

// SecurityConfig without ldap use for testing
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        http
        //                .csrf().disable().authorizeRequests()
        //                .antMatchers("/authenticate", "/h2-console/**")
        //                .permitAll()
        http.cors(cores -> {
                CorsConfigurationSource corsConfigurationSource = corsConfigurationSource();
                cores.configurationSource(corsConfigurationSource);
            })
            //                .csrf().ignoringAntMatchers("/h2-console/**").and().headers().frameOptions().sameOrigin().and()
            .csrf().disable().authorizeRequests()
            .antMatchers("/login", "/authenticate", "/h2-console/**",
                "/es/**"
                , "/group/**", "/function/**", "/session/**", "/sub/**", "/user/**", "/subscription/**"
            ).permitAll()
            //                .and().csrf().ignoringAntMatchers("/authenticate", "/h2-console/*", "/es/*"
            //                , "/group/*", "/function/", "/session/", "/sub/", "/user/*")
            //                .and().headers().frameOptions().sameOrigin()
            //                .and().authorizeRequests().antMatchers("/add").hasAnyRole("ADMIN", "STAFF")
            //                .and().authorizeRequests().antMatchers("/update/**").hasRole("STAFF")
            .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedPage("/accessDenied.jsp")//for 404 error otherwise its return 403 forbidden error
            .and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    //    @Bean
    //    @Override
    //    protected UserDetailsService userDetailsService() {
    //        UserDetails timmy = User.builder().username("danish").password("password").roles("admin").build();
    //        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager(timmy);
    //
    //        return userDetailsManager;
    //    }

    //    http
    //        .authorizeRequests()
    //        .antMatchers("/products/**").permitAll()
    //          .and()
    //          .authorizeRequests()
    //          .antMatchers("/customers/**").hasRole("ADMIN")
    //          .anyRequest().authenticated()
    //          .and()
    //          .httpBasic();

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        //		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        //		configuration.setExposedHeaders(Arrays.asList("Authorization"));
        //		configuration.setAllowCredentials(true);
        //		configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}