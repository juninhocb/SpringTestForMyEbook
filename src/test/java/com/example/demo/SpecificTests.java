package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles( {"specific", "mysql-tests"})
@SpringBootTest
class SpecificTests {
    @Value("${my.specific.property}")
    private String prop;

    @Test
    @DisplayName("Must certify that this class is running " +
            "in the specified profile, taking its property")
    void testSpecificProperty(){
        assertThat(prop).isNotNull();
        assertThat(prop).isEqualTo("wh4a2tb");
    }
}
