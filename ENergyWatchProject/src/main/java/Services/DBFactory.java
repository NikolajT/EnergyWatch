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

public class DBFactory implements IDBFactory {

    private static final String URL = "localhost";
    private static final String MongoPORT = "27017";
    private static final String MySQLPort = "3306";
    public static final Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + URL + ":" + MySQLPort, "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String DATABASE_NAME = "db";
    /**
     * Lazy singleton for DBConnection
     **/
    private static DBFactory instance;
    private static MongoDatabase database;


    private DBFactory() {
        //constructor
//        initializeMongoDatabase();
        initializeMQSQLDatabase();
    }

    public static DBFactory getInstance() {
        if (instance == null) {
            instance = new DBFactory();
        }
        return instance;
    }

    //    private void initializeMongoDatabase() {
//        try {
//
//            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
//                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));
//            MongoClientSettings settings = MongoClientSettings.builder()
//                    .codecRegistry(pojoCodecRegistry)
//                    .applyConnectionString(new ConnectionString("mongodb://" + URL + ":" + PORT))
//                    .build();
//            MongoClient mongoClient = MongoClients.create(settings);
//            database = mongoClient.getDatabase(DATABASE_NAME);
//        } finally {
//            if (database == null) System.exit(-1);
//        }
//    }
    private Connection initializeMQSQLDatabase() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Database connection established");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW DATABASES");
            while (resultSet.next()) {
                String databaseName = resultSet.getString("Database");
                System.out.println(databaseName);
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
            if (database == null) System.exit(-1);
        }
        return connection;
    }

//    @Override
//    public List<EnergyPrice> getEnergyPriceMongo() {
//        MongoCollection<EnergyPrice> mongoCollection = database.getCollection("Energy", EnergyPrice.class);
//        return mongoCollection.find().into(new ArrayList<>());
//    }

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


