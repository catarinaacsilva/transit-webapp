package ua.es.transit.incident;

public class GeoPoint {
    protected final double lat, lon;

    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return lat+","+lon;
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;

        // field comparison
        GeoPoint p = (GeoPoint) o;
        return lat == p.lat && lon == p.lon;
    }

    @Override
    public final int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + (int)lat;
        result = prime * result + (int)lon;
        return result;
    }
}
