package com.missuo.server.config;

import com.missuo.server.interceptor.JwtTokenAdminInterceptor;
import com.missuo.server.interceptor.JwtTokenUserInterceptor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

  private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;
  private final JwtTokenUserInterceptor jwtTokenUserInterceptor;

  public void addInterceptors(InterceptorRegistry registry) {
    log.info("Start registering a custom interceptor...");
    registry
        .addInterceptor(jwtTokenAdminInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/admin/employee/login");
    registry
        .addInterceptor(jwtTokenUserInterceptor)
        .addPathPatterns("/user/**")
        .excludePathPatterns("/user/user/login")
        .excludePathPatterns("/user/shop/status");
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
}
