package rs.ac.uns.ftn.nistagram.campaign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class SpringNistagramCampaignServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramCampaignServiceApplication.class, args);
    }

}
