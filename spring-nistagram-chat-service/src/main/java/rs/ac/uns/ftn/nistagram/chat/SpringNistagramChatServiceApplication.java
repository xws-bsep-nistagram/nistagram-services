package rs.ac.uns.ftn.nistagram.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringNistagramChatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramChatServiceApplication.class, args);
    }

}
