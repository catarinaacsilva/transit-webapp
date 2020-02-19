package ua.es.transit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidentService {

    public static List<Incident> getList(GeoPoint p1, GeoPoint p2, String key){
        List<Incident> list = new ArrayList<>();

        try {
            URL url = new URL("http://dev.virtualearth.net/REST/v1/Traffic/Incidents/"+p1+","+p2);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("key", key);

            System.out.println("URL: "+url);

            System.out.println(ParameterStringBuilder.getParamsString(parameters));

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
            out.flush();
            out.close();


            int status = con.getResponseCode();

            System.out.println(status);

            BufferedReader streamReader = null;

            if (status > 299) {
                streamReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            } else {
                streamReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }


            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = streamReader.readLine()) != null) {
                content.append(inputLine);
            }
            streamReader.close();

            con.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
