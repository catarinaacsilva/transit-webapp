package ua.es.transit.incident;



import ua.es.transit.Config;
import ua.es.transit.http.HTTP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Incident {
    private final String description;
    private final Date lastModified;
    private final boolean roadClosed;
    private final GeoPoint point;
    private final int type;

    private static final Pattern pattern = Pattern.compile("\\(([^)]+)\\)");

    public Incident(String description, Date lastModified, boolean roadClosed, GeoPoint point, int type) {
        this.description = description;
        this.lastModified = lastModified;
        this.roadClosed = roadClosed;
        this.point = point;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public boolean isRoadClosed() {
        return roadClosed;
    }

    public GeoPoint getPoint() {
        return point;
    }

    public int getType() {
        return type;
    }

    public static List<Incident> getIncidents(double lat0, double lon0, double lat1, double lon1) {
        return getIncidents(new GeoPoint(lat0, lon0), new GeoPoint(lat1, lon1));
    }

    public static List<Incident> getIncidents(GeoPoint p1, GeoPoint p2) {
        List<Incident> list = new ArrayList<>();

        // Todas as keys ficam guardadas no ficheiro config.cfg
        Config cfg = new Config();
        String key = cfg.getProperty("key");

        try {
            Map<String, Object> mapJson = HTTP.getJson("http://dev.virtualearth.net/REST/v1/Traffic/Incidents/" + p1 + "," + p2 + "?key=" + key);
            //System.out.println(mapJson);
            //ObjectMapper mapper = new ObjectMapper();
            //String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mapJson);
            //String json = mapper.writeValueAsString(mapJson);
            //System.out.println(json);
            List<Object> resourceSets = (List<Object>) mapJson.get("resourceSets");

            for (Object o1 : resourceSets) {
                Map<String, Object> resourceSet = (Map<String, Object>) o1;
                List<Object> resources = (List<Object>) resourceSet.get("resources");
                for (Object o2 : resources) {
                    Map<String, Object> resource = (Map<String, Object>) o2;
                    Map<String, Object> point = (Map<String, Object>) resource.get("point");
                    List<Object> coordinates = (List<Object>) point.get("coordinates");
                    GeoPoint p = new GeoPoint((Double) coordinates.get(0), (Double) coordinates.get(1));
                    String date = (String) resource.get("lastModified");
                    Matcher m = pattern.matcher(date);
                    m.find();
                    Date d = new Date(Long.parseLong(m.group(1)));
                    System.out.println(date);
                    list.add(new Incident((String) resource.get("description"), d,
                            (Boolean) resource.get("roadClosed"), p, (Integer) resource.get("type")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String toString() {
        return String.format("Type: %d%nDescription: %s%nLocation: %s%nRoad Closed: %s%nLast Modified: %s%n",
                type, description, point, roadClosed, lastModified);
    }
}
