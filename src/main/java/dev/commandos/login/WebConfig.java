package dev.commandos.login;

import dev.commandos.login.web.filter.LogFilter;
import dev.commandos.login.web.filter.LoginCheckFilter;
import dev.commandos.login.web.interceptor.LogInCheckInterceptor;
import dev.commandos.login.web.interceptor.LogInterceptor;
import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.validation.constraints.NotEmpty;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /*
     * 필터 등록하기
     * 필터 등록 방법 중 FilterRegistrationBean 를 사용해서 필터를 등록하자.
     * 필터 순서 조절기능도 있고 좋음
     *
     * 인터셉터 등록하기
     * WebMvcConfigurer 를 구현한다.
     * addInterceptors 를 오버라이드 해준다.
     */

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");

        registry.addInterceptor(new LogInCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/add", "/login",
                        "/logout","/css/**");
    }

    //@Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    //@Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
