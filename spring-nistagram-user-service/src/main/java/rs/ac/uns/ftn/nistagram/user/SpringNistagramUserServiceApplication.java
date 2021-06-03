package rs.ac.uns.ftn.nistagram.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SpringNistagramUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNistagramUserServiceApplication.class, args);

	}
}
