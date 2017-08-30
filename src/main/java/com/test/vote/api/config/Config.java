package com.test.vote.api.config;

import com.test.vote.TestDataInitializer;
import org.springframework.context.annotation.*;

/**
 * @author Lokki17
 * @since 29.08.2017
 */
@Configuration
@ComponentScan({
        "com.test.vote"
})
public class Config {

    @Profile("initdb")
    @Bean
    @Lazy(false)
    TestDataInitializer testDataInitializer() {
        return new TestDataInitializer();
    }

}
