package ua.es.transit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.es.transit.incident.Incident;

import java.util.List;

@Controller
@RestController
@RequestMapping("/")
public class mainController {

    @RequestMapping("/")
    public String getServerInfo() {
        return "index.html";
    }

    @GetMapping("/incidents")
    public List<Incident> getIncidents(@RequestParam double lat0, @RequestParam double lon0,
                                       @RequestParam double lat1, @RequestParam double lon1) {
        return Incident.getIncidents(lat0, lon0, lat1, lon1);
    }
}
