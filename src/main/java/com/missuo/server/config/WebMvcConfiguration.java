package com.missuo.server.config;

import com.missuo.common.json.JacksonObjectMapper;
import com.missuo.server.interceptor.JwtTokenAdminInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

  @Autowired private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

  protected void addInterceptors(InterceptorRegistry registry) {
    log.info("Start registering a custom interceptor...");
    registry
        .addInterceptor(jwtTokenAdminInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/admin/employee/login");
  }

  @Bean
  public GroupedOpenApi adminApi() {
    return GroupedOpenApi.builder()
        .group("Admin")
        .packagesToScan("com.missuo.server.controller.admin")
        .build();
  }

  @Bean
  public GroupedOpenApi userApi() {
    return GroupedOpenApi.builder()
        .group("User")
        .packagesToScan("com.missuo.server.controller.user")
        .build();
  }

  private Info apiInfo() {
    return new Info()
        .title("Interface documentation")
        .version("1.0")
        .description("Interface documentation");
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(apiInfo());
  }

  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    log.info("Start registering Swagger static resources...");
    registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
    registry
        .addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Override
  protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    log.info("Start registering a custom message converter...");
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
        new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
    // https://blog.csdn.net/weixin_55217876/article/details/131971496
    converters.add(1, mappingJackson2HttpMessageConverter);
  }
}
