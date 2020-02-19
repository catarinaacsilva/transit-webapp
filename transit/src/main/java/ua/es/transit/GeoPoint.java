package ua.es.transit;

public class GeoPoint {
    private final float lat, lon;

    public GeoPoint(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String toString() {
        return lat+","+lon;
    }
}
