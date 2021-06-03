package rs.ac.uns.ftn.nistagram.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class SpringNistagramApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNistagramApiGatewayApplication.class, args);
	}

}
