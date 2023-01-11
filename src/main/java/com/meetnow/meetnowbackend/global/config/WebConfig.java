package com.meetnow.meetnowbackend.global.config;

import com.meetnow.meetnowbackend.global.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .order(1)
                .addPathPatterns("/rooms/**", "/timetables/**")
        ;
    }



    // 아래 Cors 코드 모바일앱이라 필요없음
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 이 경로로 접근하는
//                .allowedOrigins("*") // 해당 Origin에 대해 CORS 활성화
//                .allowedMethods(// 허용할 HTTP Method 설정
//                        "*"
//                );
//    }

}














