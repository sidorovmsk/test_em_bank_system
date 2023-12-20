package spring_boot_java.test_itfb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import spring_boot_java.test_itfb.config.AppConfig;

@SpringBootApplication
@Import(AppConfig.class)
public class TestEMApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestEMApplication.class, args);
    }
}
