package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//시작 단계에서 프레임워크가 이 클래스 안에 우리가 정의한 모든 bean을 스캔
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //강의 내용이 구버전 작성방식이라 버전에 맞게 최신 작성법으로 작성함

        /*
        * Below is the custom security configurations
        * */

        http
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())


                .authorizeHttpRequests(
                request -> request

                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()// 경로 보안
                .requestMatchers("notices", "/contact").permitAll()// 경로 허용
        );
        
        /*
        * Configuration to deny all the requests
        * */

        /*http
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())


                .authorizeHttpRequests(
                        request -> request
                                .anyRequest().denyAll()
                );*/

        /*
         * Configuration to permit all the requests
         * */

        /*http
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())


                .authorizeHttpRequests(
                        request -> request
                                .anyRequest().permitAll()
                );*/

        return http.build();
    }


}
