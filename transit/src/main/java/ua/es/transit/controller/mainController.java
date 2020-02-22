package ua.es.transit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {

    @RequestMapping("/")
    public String getServerInfo(){
        return "index.html";
    }
}
