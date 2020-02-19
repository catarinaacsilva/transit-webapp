package ua.es.transit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IncidentService {

    public static List<Incident> getList(GeoPoint p1, GeoPoint p2){
        List<Incident> list = new ArrayList<>();

        // Todas as keys ficam guardadas no ficheiro config.cfg
        Config cfg = new Config();
        String key = cfg.getProperty("key");

        try {
            URL url = new URL("http://dev.virtualearth.net/REST/v1/Traffic/Incidents/"+p1+","+p2+"?key="+key);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setAllowUserInteraction(true);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int status = con.getResponseCode();

            System.out.println(status);

            boolean isError = (con.getResponseCode() >= 400);
            InputStream is = isError ? con.getErrorStream() : con.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonMap = mapper.readValue(is, Map.class);




            System.out.println(jsonMap);

            con.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
