package ua.es.transit;


public class Incident {
   private final String description;
   private final String detour;
   private final String lastModified;
   private final boolean roadClosed;
   private final GeoPoint point;

   public Incident(String description, String detour, String lastModified, boolean roadClosed, GeoPoint point){
       this.description = description;
       this.detour = detour;
       this.lastModified = lastModified;
       this.roadClosed = roadClosed;
       this.point = point;
   }
}
