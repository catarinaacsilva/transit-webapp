package ua.es.transit.incident;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.es.transit.config.Config;
import ua.es.transit.http.HTTP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IncidentService {

    private static final Pattern pattern = Pattern.compile("\\(([^)]+)\\)");

    @Cacheable(value="boundingbox", key="BoundingBox(#lat0, #lon0, #lat1, #lon1)")
    public static List<Incident> getIncidents(double lat0, double lon0, double lat1, double lon1) {
        return getIncidents(new GeoPoint(lat0, lon0), new GeoPoint(lat1, lon1));
    }

    @Cacheable(value="boundingbox", key="BoundingBox(#p1, #p2)")
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
                    list.add(new Incident((String) resource.get("description"), d,
                            (Boolean) resource.get("roadClosed"), p, (Integer) resource.get("type")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
