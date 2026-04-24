package org.iu.example.rest_starter_example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstRestController {

    @GetMapping("/helloworld")
    public String helloWorld(@RequestParam(name = "firstname") String name) {
        return String.format("Hello, %s!", name);
    }
}
