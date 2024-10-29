package com.zhny.computer.config;


import com.zhny.computer.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class UserConfig implements WebMvcConfigurer {

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/protected/**","/web/collection.html",
                        "/web/profileCarts.html",
                        "/web/autoCreateProfileBySelect.html",
                        "/web/userProfile.html",
                        "/web/AI.html",
                        "/web/autoCreateProfile.html",
                        "/web/profileList.html",
                        "/web/recommendProfile.html",
                        "/web/searchProduct.html") // 需要拦截的路径
                .excludePathPatterns("/web/login.html",
                        "/web/register.html",
                        "/web/kowledge.html",
                        "/web/index.html",
                        "/web/product.html"); // 放行的路径
    }
}
