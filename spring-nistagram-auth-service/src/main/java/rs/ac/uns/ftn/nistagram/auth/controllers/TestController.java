package rs.ac.uns.ftn.nistagram.auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/test")
public class TestController {

    @GetMapping
    public void test(){
        System.out.println("Hello from auth service");
    }

}
