package com.metro.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = UserServiceApplication.class, properties = "JWT_SIGNER_KEY=some-test-secret")
@ActiveProfiles("test")
class UserServiceApplicationTests {

    @Test
    void contextLoads() {}
}
