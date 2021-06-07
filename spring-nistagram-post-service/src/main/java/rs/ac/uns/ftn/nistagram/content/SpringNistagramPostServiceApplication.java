package rs.ac.uns.ftn.nistagram.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringNistagramPostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramPostServiceApplication.class, args);
    }

}
