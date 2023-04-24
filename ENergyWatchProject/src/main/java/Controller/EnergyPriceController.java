package Controller;

import Model.EnergyPrice;
import Services.DBFactory;
import Services.EventListener;
import Services.IDBFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EnergyPriceController implements EventListener {
    IDBFactory factoryService = DBFactory.getInstance();

    protected void store(String dataString) {
        EnergyPrice energyPrice;
        Gson gson = new Gson();
        String json = dataString;
        energyPrice = gson.fromJson(json, EnergyPrice.class);

        factoryService.createEnergyPrice(energyPrice);


    }
    protected void postPriceData() {
        List<EnergyPrice> payload = factoryService.getEnergyPrice();


        String json = new Gson().toJson(payload);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/energyPrice"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }


    @Override
    public void update(String eventtype, String energyData) {
        System.out.println("EnergyPriceController: " + eventtype + " " + energyData);
        store(energyData);
    }
}
