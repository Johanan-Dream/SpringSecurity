package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration//시작 단계에서 프레임워크가 이 클래스 안에 우리가 정의한 모든 bean을 스캔
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //강의 내용이 구버전 작성방식이라 버전에 맞게 최신 작성법으로 작성함

        http
                //csrf 비활성화 : 강의에선 csrf가 기본 설정되어 있어서 비활성화함
                //6.1 ~ 부터는 비활성화 되어있음
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())


                .authorizeHttpRequests(
                request -> request

                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()// 경로 보안
                .requestMatchers("notices", "/contact", "/register").permitAll()// 경로 허용
        );

        return http.build();
    }

    /*
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        *//* Approach 1 when we use withDefaultPasswordEncoder() method
        while creating the user details *//*

        *//* UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("1234")
                .authorities("admin")
                .build();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("1234")
                .authorities("read")
                .build();*//*

        *//* Approach 2 where we use NoOpPasswordEncoder Bean
        while creating the user details *//*

        UserDetails admin = User.withUsername("admin")
                .password("1234")
                .authorities("admin")
                .build();
        UserDetails user = User.withUsername("user")
                .password("1234")
                .authorities("read")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }
    */
    
    //userDetailsService를 구현해두었기 때문에 충돌 발생
    /*    
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
    */

    /*
    * NoOpPasswordEncoder is not recommended for production usage.
    * User only for non-prod.
    *
    * @return PasswordEncoder
    * */
    // PasswordEncoder가 항상 필요한 이유?
    // Spring Security에게 항상 비밀번호를 어떻게 저장했는지 알려주어야 하기 때문(일반 텍스트/암호화와 해싱 등등..)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
