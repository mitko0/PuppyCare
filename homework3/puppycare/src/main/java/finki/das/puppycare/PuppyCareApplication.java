package finki.das.puppycare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class PuppyCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuppyCareApplication.class, args);
    }

}
