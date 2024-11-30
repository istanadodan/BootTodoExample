package ksd.sto.ndm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BoardApplicationTests {

    @Autowired
    private PasswordEncoder encoder;
    @Test
    void contextLoads() {
        String passwordString = "1111";
        String enPwString = encoder.encode(passwordString);
        System.out.println(enPwString);
    }
}
