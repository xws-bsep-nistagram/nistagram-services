package rs.ac.uns.ftn.nistagram.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringNistagramFeedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramFeedServiceApplication.class, args);
    }

}
