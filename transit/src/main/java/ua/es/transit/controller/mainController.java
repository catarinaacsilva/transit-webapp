package ua.es.transit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.es.transit.incident.Incident;

import java.util.List;

@Controller

@RequestMapping("/")
public class mainController {

    @RequestMapping("/")
    public String getIndex() {
        return "index.html";
    }
    @RequestMapping("/statistics")
    public String getStatistics() {
        return "statistics.html";
    }
}
