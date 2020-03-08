package ua.es.transit.incident;

import javax.persistence.Id;
import java.io.Serializable;

public class IncidentId implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String description;
    private final double lat;
    private final double lon;

    public IncidentId() {
        description = "";
        lat = 0;
        lon = 0;
    }

    public IncidentId(final String description, final double lat, final double lon) {
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public String getDescription() {
        return description;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
}
