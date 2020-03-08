package ua.es.transit.incident;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(IncidentId.class)
public class Incident implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private final String description;
    private final Date lastModified;
    private final boolean roadClosed;
    @Id
    private final double lat;
    @Id
    private final double lon;
    private final int type;

    public Incident() {
        description = "";
        lat = 0;
        lon = 0;
        lastModified = new Date();
        roadClosed = false;
        type = -1;
    }

    public Incident(String description, Date lastModified, boolean roadClosed, double lat, double lon, int type) {
        this.description = description;
        this.lastModified = lastModified;
        this.roadClosed = roadClosed;
        this.lat = lat;
        this.lon = lon;
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

    public GeoPoint getPoint() { return new GeoPoint(lat, lon); }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("Type: %d%nDescription: %s%nLocation: %s%nRoad Closed: %s%nLast Modified: %s%n",
                type, description, getPoint(), roadClosed, lastModified);
    }
}
