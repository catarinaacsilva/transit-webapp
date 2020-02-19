package ua.es.transit;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Incident> incidents = IncidentService.getList(new GeoPoint(0,0), new GeoPoint(1,1), "bananan");
    }
}
