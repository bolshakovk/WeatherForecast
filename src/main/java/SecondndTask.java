import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SecondndTask {
    public static void main(String[] args) {
        String output = getUrlContent("https://api.openweathermap.org/data/2.5/" +
                "onecall?lat=56.3287&lon=44.002&exclude=current,hourly,minutely,alerts&" +
                "appid=34a4801037bcd9496bca871a0688a1d6&units=metric&units=metric");

        if (!output.isEmpty()){
            try {
                JSONObject object = new JSONObject(output);
                double difference;
                int myDay = (int) object.getJSONArray("daily").getJSONObject(4).get("dt");
                double current = Math.abs((double) object.getJSONArray("daily").getJSONObject(7).getJSONObject("temp").get("night"));
                for (int day = 0; day < 4; day++){
                    double sunrise  =  object.getJSONArray("daily").getJSONObject(day).getDouble("sunrise");
                    double sunset =  object.getJSONArray("daily").getJSONObject(day).getDouble("sunset");
                    int tmpDay = (int) object.getJSONArray("daily").getJSONObject(day).get("dt");
                    difference = Math.abs(sunrise - sunset);
                    if (current < difference){
                        current = difference;
                        myDay = tmpDay;
                    }
                    System.out.println("biggest in " + day + " = " + difference);
                }
                System.out.println("biggest is " + current + " in day " + myDay);

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
