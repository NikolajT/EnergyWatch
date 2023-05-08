package Services;

import Model.EnergyPrice;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Defininerer og implementerer metoder til at oprette, læse, opdatere og slette data i databasen
 **/
public class DBFactory implements IDBFactory {
    /**
     * Klassevariabler
     * **/
    public static final Connection connection;
    private static final String URL = "localhost";
    private static final String MongoPORT = "27017";
    private static final String MySQLPort = "3306";
    private static final String DATABASE_NAME = "db";
    /**
     * Lazy singleton for DBConnection
     **/
    private static DBFactory instance;
    private static MongoDatabase database;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + URL + ":" + MySQLPort, "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Default constructor
     * **/
    private DBFactory() {
        //constructor
//        initializeMongoDatabase();
        initializeMySQLDatabase();
    }


    public static DBFactory getInstance() {
        if (instance == null) {
            instance = new DBFactory();
        }
        return instance;
    }
    /**
     *
     * **/

    private Connection initializeMySQLDatabase() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Database connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                String databaseName = resultSet.getString("Database");
                System.out.println(databaseName);
                createEnergyPricesTable();
            }
            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection == null) System.exit(-1);
        }
        return connection;
    }

    public void createEnergyPricesTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS energy_prices (" +
                    "id INT NOT NULL /** SERIAL, AUTO_INCREMENT? **/,"
                    + "price_dkk FLOAT,"
                    + "area TEXT,"
                    + "date TIMESTAMP,"
                    + "PRIMARY KEY (id)"
                    + ")";
            statement.executeUpdate(createTableSql);
            System.out.println("Table energy_prices created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating energy_prices table: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public List<EnergyPrice> getEnergyPrice(Connection connection) throws SQLException {
        // Create a statement for the query

        Statement statement = connection.createStatement();

        // Execute the query and get the result set
        ResultSet resultSet = statement.executeQuery("SHOW DATABASES");

        // Create a list to hold the EnergyPrice objects
        List<EnergyPrice> energyPrices = new ArrayList<>();

        // Loop through the rows of the result set and create EnergyPrice objects
        while (resultSet.next()) {
            UUID id = UUID.fromString(resultSet.getString("id"));
            double spotPrice = resultSet.getDouble("spot_price");
            String priceArea = resultSet.getString("price_area");
            Date date = resultSet.getDate("date");
            EnergyPrice energyPrice = new EnergyPrice(id, spotPrice, priceArea, date);
            energyPrices.add(energyPrice);
        }

        // Close the result set and statement
        resultSet.close();
        statement.close();

        // Return the list of EnergyPrice objects
        return energyPrices;
    }


    @Override
    public void createEnergyPrice(EnergyPrice price) {
        MongoCollection<EnergyPrice> mongoCollection = database.getCollection("Energy", EnergyPrice.class);
        mongoCollection.insertOne(price);
    }

    @Override
    public void delete(EnergyPrice id) {
        MongoCollection<EnergyPrice> mongoCollection = database.getCollection("Energy", EnergyPrice.class);
        mongoCollection.dropIndex(String.valueOf(id));
    }
}


