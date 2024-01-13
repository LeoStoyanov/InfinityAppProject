package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class Wcontroller {

    @GetMapping("/")
    public String welcome() {
        return "This is where the party starts";
    }

    @GetMapping("/{number}")
    public String welcome(@PathVariable String number) {
        return "The number you have chosen is: " + number + "That's a cool number!";
    }
    @GetMapping("/who")
    public String who()
    {
        return "James Root is who";
    }
    @GetMapping("/why")
    public String why()
    {
        return "Chickens fly";
    }
}
