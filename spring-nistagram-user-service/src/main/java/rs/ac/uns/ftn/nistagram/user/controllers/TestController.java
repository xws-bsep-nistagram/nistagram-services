package rs.ac.uns.ftn.nistagram.user.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/test")
public class TestController {

    @GetMapping
    public void test(){
        System.out.println("Hello from user service");
    }

}
