package rs.ac.uns.ftn.nistagram.media.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/media/test")
public class TestController {

    @GetMapping
    public void test(){
        System.out.println("Hello from media service");
    }

}
