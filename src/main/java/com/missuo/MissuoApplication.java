package com.missuo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MissuoApplication {

  public static void main(String[] args) {
    SpringApplication.run(MissuoApplication.class, args);
  }
}
