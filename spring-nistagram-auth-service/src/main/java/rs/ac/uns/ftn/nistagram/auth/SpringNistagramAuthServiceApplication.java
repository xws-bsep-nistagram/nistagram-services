package rs.ac.uns.ftn.nistagram.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringNistagramAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramAuthServiceApplication.class, args);
    }

}
