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
 * Data fra starten af året til nu. Priserne time for time for DK1 og DK2-områderne
 **/
public class APIConnection {


    public static void getContent() throws Exception {
        URL url = null;
        url = new URL("https://api.energidataservice.dk/dataset/Elspotprices?start=now-P1Y&end=now&filter={%22PriceArea%22:[%22DK1%22,%22DK2%22]}&limit=2");

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
        in.close();
        System.out.println(content); /** Outputs JSON. Plan: Parse JSON and use as input in store-method at EnergyPriceController**/
    }

    public static void main(String[] args) throws Exception {
        getContent();
    }
}
