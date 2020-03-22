package pt.ua.deti;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility library.
 * 
 * @author Catarina Silva
 * @version 1.0
 */
public class Utils {
    /**
     * Utility class, lets make the constructor private.
     */
    private Utils() {}
  
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
      return (T) o;
    }

    /**
     * Convert a {@link Map} into a string.
     * <p>
     * Useful to print the result into a log
     * @param map {@link Map} to convert
     * @return a {@String} formated with the content of the map
     */
    public static String convertWithStream(Map<String, ?> map) {
        String mapAsString = map.keySet().stream()
          .map(key -> key + "=" + map.get(key))
          .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }
}