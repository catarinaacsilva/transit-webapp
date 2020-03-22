package ua.es.transit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class mainController {
    @RequestMapping(value={"/", "/accident", "/congestion", "/disabledvehicle", "/masstransit", "/miscellaneous",
            "/othernews", "/plannedevent", "/roadhazard", "/construction", "/alert", "/weather"})
    public String getIndex() {
        return "index.html";
    }

    @RequestMapping("/statistics")
    public String getStatistics() {
        return "statistics.html";
    }

    @RequestMapping("/notifications")
    public String getNotifications() {
        return "notifications.html";
    }
}
