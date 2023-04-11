package Services;

import com.mongodb.ConnectionString;
import com.mongodb.Mongo;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class DBConnection {

    private static final String URL = "localhost";
    private static final String PORT = "27017";
    private static final String DATABASE_NAME = "EnergyDB";
    private static final String COLLECTION_NAME = "energy_Data";
    /**
     * Lazy singleton for DBConnection
     **/
    private static DBConnection instance;
    private static MongoDatabase database;


    private DBConnection() {
        //constructor
        initializeMongoDatabase();

    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }


    public static void main(String[] args) {
        // Get a handle to the MongoDB database
        DBConnection database = DBConnection.getInstance();

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
}

