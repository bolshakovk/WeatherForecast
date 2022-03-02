import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) {
        String output = getUrlContent("https://api.openweathermap.org/data/2.5/" +
                "onecall?lat=56.3287&lon=44.002&exclude=current,hourly,minutely,alerts&" +
                "appid=34a4801037bcd9496bca871a0688a1d6&units=metric&units=metric");
        if (!output.isEmpty()){
            try {
                JSONObject object = new JSONObject(output);
                double difference;
                double current = Math.abs((double) object.getJSONArray("daily").getJSONObject(0).getJSONObject("temp").get("night"));
                for (int day = 0; day < 7; day++){
                    double minimal  = (double) object.getJSONArray("daily").getJSONObject(day).getJSONObject("temp").get("night");
                    double feelsLike = (double) object.getJSONArray("daily").getJSONObject(day).getJSONObject("feels_like").get("night");
                    difference = Math.abs(minimal - feelsLike);
                    if (current > difference){
                        current = difference;
                    }
                    System.out.println("difference in " + day + " = " + difference);
                }
                System.out.println("lowest is " + current);
                object.getJSONArray("daily").getJSONObject(1).get("temp");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("wrong city");
            e.printStackTrace();
        }
        return content.toString();
    }
}
