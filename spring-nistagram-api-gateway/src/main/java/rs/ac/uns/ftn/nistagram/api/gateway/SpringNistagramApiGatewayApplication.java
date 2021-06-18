package rs.ac.uns.ftn.nistagram.api.gateway;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableZuulWebSocket
@EnableZuulProxy
public class SpringNistagramApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringNistagramApiGatewayApplication.class, args);
    }

}
