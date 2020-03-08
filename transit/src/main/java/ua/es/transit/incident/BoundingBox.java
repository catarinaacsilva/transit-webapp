package ua.es.transit.incident;

import java.io.Serializable;

public class BoundingBox implements Serializable {
    private static final long serialVersionUID = 1L;
    private final GeoPoint NW, SE;

    public BoundingBox(double lat0,  double lon0, double lat1,  double lon1) {
       this(new GeoPoint(lat0, lon0), new GeoPoint(lat1, lon1));
    }

    public BoundingBox(final GeoPoint p1, final GeoPoint p2) {
        this.NW = p1;
        this.SE = p2;
    }

    public boolean inside(GeoPoint p) {
        if( ( p.lat <= NW.lat && p.lat >= SE.lat ) && ( p.lon >= NW.lon && p.lon <= SE.lon ) ) {
            return true;
        }
        return false;
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
        BoundingBox bb = (BoundingBox) o;
        boolean rv = this.NW.equals(bb.NW) && this.SE.equals(bb.SE);
        if (!rv) {
            rv = inside(bb.NW) && inside(bb.SE);
        }
        return rv;
    }

    @Override
    public final int hashCode() {
        return NW.hashCode() + SE.hashCode();
    }

    @Override
    public String toString() {
        return "["+NW.toString()+"]["+SE.toString()+"]";
    }
}
