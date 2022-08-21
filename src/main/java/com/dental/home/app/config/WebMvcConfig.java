package com.dental.home.app.config;

import com.dental.home.app.interceptor.ManageSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${environment}")
    private String environment;

    /**
     * manageSessionInterceptor
     */
    @Autowired
    private ManageSessionInterceptor manageSessionInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理后台拦截
        InterceptorRegistration manageInterceptorRegistration = registry.addInterceptor(manageSessionInterceptor);
        manageInterceptorRegistration.addPathPatterns("/pc/manage/**");
        manageInterceptorRegistration.excludePathPatterns(Arrays.asList(new String[]{"/common/file/downFile/**","/pc/manage/test/**"}));

    }

    //取消拦截swagger
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if("dev".equals(environment)){
            registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }
}
