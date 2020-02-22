package ua.es.transit.incident;

public class GeoPoint {
    private final double lat, lon;

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

    public String toString() {
        return lat+","+lon;
    }
}
