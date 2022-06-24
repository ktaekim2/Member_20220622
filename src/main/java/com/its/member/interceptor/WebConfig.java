package com.its.member.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 해당 클래스의 설정 정보를 스프링 컨테이너에 등록
// 인터셉터 안쓰려면 @Configuration만 주석처리하면 기능 멈춤
public class WebConfig implements WebMvcConfigurer { // 인터페이스 구현클래스
    @Override // @Override: 매서드 재정의
    // WebMvcConfigurer 인터페이스의 메서드들은 추상메서드가 아니기 때문에 재정의 가능
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor((new LoginCheckInterceptor())) // 어떤 인터셉터 쓸래?
                // 체이닝 매서드: 연달아 매서드를 불러 씀
                .order(1) // 해당 인터셉터의 우선순위
                .addPathPatterns("/**") // 어떤 주소를 인터셉터로 체크할건지(모든 주소)
                .excludePathPatterns("/", "/member/save-form", "/member/login-form", "/member/login",
                        "/member/save", "/member/logout", "/member/duplicate-check", "/js/**",
                        "/css/**", "/*.ico", "/error", "/images/**", "/favicon/**");
        // 제외 주소(로그인을 안해도 접근 가능)
        // 로그인을 하지 않아도 접속 가능한 주소
    }

}
