package com.save.codesnippetapp.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ProductServiceInterceptorAppConfig(private val apiInterceptor: ApiInterceptor) : WebMvcConfigurer {

    @Override
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(apiInterceptor)
    }
}
