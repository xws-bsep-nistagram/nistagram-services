package rs.ac.uns.ftn.nistagram.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringNistagramNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramNotificationServiceApplication.class, args);
    }

}
