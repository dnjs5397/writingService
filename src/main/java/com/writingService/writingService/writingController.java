package com.writingService.writingService;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class writingController {

    @GetMapping("/")
    public String empty() {
        return "redirect:/home";
    }

    @GetMapping("home")
    public String home() {
        return "index";
    }


}
