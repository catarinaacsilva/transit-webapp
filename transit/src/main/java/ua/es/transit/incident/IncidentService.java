package ua.es.transit.incident;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.es.transit.config.Config;
import ua.es.transit.http.HTTP;
import ua.es.transit.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IncidentService {

    private static final Pattern pattern = Pattern.compile("\\(([^)]+)\\)");

    @Autowired
    IncidentRepository repository;

    public void save(final Incident incident) {
        repository.save(incident);
    }

    @Cacheable(value="boundingbox", key="new ua.es.transit.incident.BoundingBox(#lat0, #lon0, #lat1, #lon1)")
    public List<Incident> getIncidents(final double lat0, final double lon0, final double lat1, final double lon1) {
        return getIncidents(new GeoPoint(lat0, lon0), new GeoPoint(lat1, lon1));
    }

    @Cacheable(value="boundingbox", key="new ua.es.transit.incident.BoundingBox(#p1, #p2)")
    public List<Incident> getIncidents(final GeoPoint p1, final GeoPoint p2) {
        final List<Incident> list = new ArrayList<>();

        // Todas as keys ficam guardadas no ficheiro config.cfg
        final Config cfg = new Config();
        final String key = cfg.getProperty("key");

        try {
            final Map<String, Object> mapJson = HTTP.getJson("http://dev.virtualearth.net/REST/v1/Traffic/Incidents/" + p1 + "," + p2 + "?key=" + key);
            final List<Object> resourceSets = Utils.cast(mapJson.get("resourceSets"));

            for (final Object o1 : resourceSets) {
                final Map<String, Object> resourceSet = Utils.cast(o1);
                final List<Object> resources = Utils.cast(resourceSet.get("resources"));
                for (final Object o2 : resources) {
                    final Map<String, Object> resource = Utils.cast(o2);
                    final Map<String, Object> point = Utils.cast(resource.get("point"));
                    final List<Object> coordinates = Utils.cast(point.get("coordinates"));
                    final String date = (String) resource.get("lastModified");
                    final Matcher m = pattern.matcher(date);
                    m.find();
                    final Date d = new Date(Long.parseLong(m.group(1)));
                    list.add(new Incident((String) resource.get("description"), d, (Boolean) resource.get("roadClosed"),
                            (Double) coordinates.get(0), (Double) coordinates.get(1), (Integer) resource.get("type")));
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
