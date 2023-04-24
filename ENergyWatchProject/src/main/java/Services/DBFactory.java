package Services;

import Model.EnergyPrice;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBFactory implements IDBFactory {

    private static final String URL = "localhost";
    private static final String PORT = "27017";
    private static final String DATABASE_NAME = "EnergyDB";
    private static final String COLLECTION_NAME = "energy_Data";
    /**
     * Lazy singleton for DBConnection
     **/
    private static DBFactory instance;
    private static MongoDatabase database;


    private DBFactory() {
        //constructor
        initializeMongoDatabase();
    }

    public static DBFactory getInstance() {
        if (instance == null) {
            instance = new DBFactory();
        }
        return instance;
    }

    private void initializeMongoDatabase() {
        try {

            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));
            MongoClientSettings settings = MongoClientSettings.builder()
                    .codecRegistry(pojoCodecRegistry)
                    .applyConnectionString(new ConnectionString("mongodb://" + URL + ":" + PORT))
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase(DATABASE_NAME);
        } finally {
            if (database == null) System.exit(-1);
        }
    }

    @Override
    public List<EnergyPrice> getEnergyPrice() {
        MongoCollection<EnergyPrice> mongoCollection = database.getCollection("Energy", EnergyPrice.class);
        return mongoCollection.find().into(new ArrayList<>());
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


