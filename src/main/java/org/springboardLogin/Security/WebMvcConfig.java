//package org.springboardLogin.Security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private JwtTokenInterceptor jwtTokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // Add the JwtTokenInterceptor and specify the URL patterns for which it should be invoked
//        registry.addInterceptor(jwtTokenInterceptor)
//                .addPathPatterns("/api/tasks"); // Add the specific URL patterns here
//    }
//}