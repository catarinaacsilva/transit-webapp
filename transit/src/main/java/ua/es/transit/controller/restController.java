package ua.es.transit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.es.transit.incident.Incident;
import ua.es.transit.incident.IncidentService;


import java.util.List;

@RestController
public class restController {

    @Autowired
    IncidentService incidentService;

    @GetMapping("/incidents")
    public List<Incident> getIncidents(@RequestParam double lat0, @RequestParam double lon0,
                                       @RequestParam double lat1, @RequestParam double lon1) {
        return incidentService.getIncidents(lat0, lon0, lat1, lon1);
    }

    @GetMapping("/stats")
    public List<Incident> getIncidentsStats(@RequestParam double lat0, @RequestParam double lon0,
                                            @RequestParam double lat1, @RequestParam double lon1) {
        List<Incident> incidents = incidentService.getIncidents(lat0, lon0, lat1, lon1);
        for(Incident i : incidents) {
            incidentService.save(i);
        }
        return incidents;
    }
}
