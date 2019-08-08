package com.gucci.mobileappws.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

    @Autowired
    Utils utils;

    @BeforeEach
    void setUp() {
    }

    @Test
    void hasTokenNotExpired() {
        String token = utils.generateEmailVerificationToken("abc");
        Utils.hasTokenExpired(token);

        assertNotNull(token);

        boolean hasTokenExpired = Utils.hasTokenExpired(token);

        assertFalse(hasTokenExpired);
    }

    @Test
    void generateUserId() {
        String userId = utils.generateUserId(30);
        String userId2 = utils.generateUserId(30);

        assertNotNull(userId);
        assertNotNull(userId2);

        assertTrue(userId.length()==30);
        assertTrue(!userId.equalsIgnoreCase(userId2));
    }

    @Test
    void hasTokenExpired(){
        String expiredToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5NVBMQW9TTzFIYlJXZ3JybzUwdW5EMDVOVkp0Z0kiLCJleHAiOjE1NTg5NzUzMjN9.n_hMNHTwgdUJxPvVawJ_bz8gqLLrb8f_dmB-Ny3ne1ktIIsO535lSB14mFsZZxEO71hYB0k8vkzu83bSyZz5Zg";

        boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);

        assertTrue(hasTokenExpired);
    }
}