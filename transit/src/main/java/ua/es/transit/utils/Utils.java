package ua.es.transit.utils;

public class Utils {
    /**
     * Utility class, lets make the constructor private.
     */
    private Utils() {}
  
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
      return (T) o;
    }
}