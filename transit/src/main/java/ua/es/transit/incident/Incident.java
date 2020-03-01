package ua.es.transit.incident;

import java.util.Date;

public class Incident {
    private final String description;
    private final Date lastModified;
    private final boolean roadClosed;
    private final GeoPoint point;
    private final int type;

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

    @Override
    public String toString() {
        return String.format("Type: %d%nDescription: %s%nLocation: %s%nRoad Closed: %s%nLast Modified: %s%n",
                type, description, point, roadClosed, lastModified);
    }
}
