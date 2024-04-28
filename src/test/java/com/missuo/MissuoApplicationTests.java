package com.missuo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MissuoApplicationTests {

  @Test
  public void testSimpleMessage() {
    System.out.println("This is a simple test message.");
  }
}
