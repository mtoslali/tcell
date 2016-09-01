package com.turkcell.bipai.saacweather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan("com.turkcell.bipai.saacweather.*")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	   registry.addViewController("/hello").setViewName("hello");
	}
	
	
	@Bean
    public BeanNameViewResolver beanViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(1);
        return resolver;
    }
	
}
