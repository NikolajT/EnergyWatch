package Controller;

import Model.EnergyPrice;
import Services.APIConnection;
import Services.DBFactory;
import Services.EventListener;
import Services.IDBFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
/**
 * Definerer en EnergyPriceController klasse, som kan hente data fra en API og gemme det i databasen.
 *
 * **/

import static Services.DBFactory.connection;

public class EnergyPriceController implements EventListener {
    IDBFactory factoryService = DBFactory.getInstance();
    /**
     * Default constructor
     * **/
    public EnergyPriceController() {
    }

    public static void main(String[] args) throws Exception {
        EnergyPriceController controller = new EnergyPriceController();
        APIConnection connection = new APIConnection();
        String energyData = connection.getContent();
        controller.store(energyData);
    }
/**
 * Gemmer data i databasen
 * **/
    protected void store(String dataString) throws SQLException {

        Gson gson = new Gson();
        String json = dataString;
// Parse the JSON data into a list of EnergyPrice objects using Gson
        List<EnergyPrice> energyPrices = new Gson().fromJson(json, new TypeToken<List<EnergyPrice>>() {
        }.getType());
// Create a StringBuilder to store the SQL statements
        StringBuilder sql = new StringBuilder();
        // Generate SQL statements to insert the energy price data into the database
        for (EnergyPrice energyPrice : energyPrices) {
            sql.append("INSERT INTO energy_prices (spot_price, price_area, date) VALUES (").append(energyPrice.getSpotPrice()).append(", ").append("'").append(energyPrice.getPriceArea()).append("', ").append("STR_TO_DATE('").append(energyPrice.getDate()).append("', '%Y-%m-%d %H:%i:%s'))").append(";\n");
        }
//        factoryService.createEnergyPrice(energyPrice);
        postPriceData();
        System.out.println("Posted to /energyprice");
        // Execute the SQL statements
        try (Connection conn = connection; Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sender dataen til vores frontend
     * **/
    protected void postPriceData() throws SQLException {
        List<EnergyPrice> payload = factoryService.getEnergyPrice(connection);

        String json = new Gson().toJson(payload);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/energyPrice")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
    }

    /**
     * Udfører store-metoden når der sker en event
     * **/
    @Override
    public void update(String eventtype, String energyData) throws SQLException {
        System.out.println("EnergyPriceController: " + eventtype + " " + energyData);
        store(energyData);
    }

}
