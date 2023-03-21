import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class APIConnection {
    public static void main(String[] args) {
        try {
            // set the API URL.
            // Data fra starten af året til nu. Priserne time for time for DK1 og NO-områderne
            URL url = new URL("https://api.energidataservice.dk/dataset/Elspotprices?start=now-P1Y&end=now&filter={\"PriceArea\":[\"DK1\",\"NO\"]}&limit=400");

            // create a connection to the API endpoint
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            // setup connection parameters
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json"); //response content type
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);


            // read the API response
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print the API response
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
