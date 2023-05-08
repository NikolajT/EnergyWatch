package Services;

import Model.EnergyPrice;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Definerer en APIConnection klasse, som kan hente data fra en API.
 *
 * **/
public class APIConnection {

    public EventManager events;
    public APIConnection() {
            this.events = new EventManager("update");
    }

public String getContent() throws Exception {
        URL url = new URL("https://api.energidataservice.dk/dataset/Elspotprices?start=now-P1Y&end=now&filter={%22PriceArea%22:[%22DK1%22,%22DK2%22]}&limit=2");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        events.notify("update", content.toString());
        in.close();
        return content.toString();
    }
}
