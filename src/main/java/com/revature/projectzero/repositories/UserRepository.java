package com.revature.projectzero.repositories;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.projectzero.util.exceptions.DataSourceException;
import com.revature.projectzero.documents.AppUser;
import com.revature.projectzero.util.MongoClientFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;


//Repository for performing CRUD operations on the Mongo user collection.

public class UserRepository implements CrudRepository<AppUser>{

    private static final String DATABASE = "p0";
    private static final String COLLECTION = "users";

    private final Logger logger = LogManager.getLogger(UserRepository.class);

    public AppUser findUserByCredentials(String username, String password) {

        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new document with provided values to query database
            Document queryDoc = new Document("username", username).append("password", password);
            // Search the database for an instance of a collection with the matching values
            Document authUserDoc = usersCollection.find(queryDoc).first();
            // Return null if the values were not propagated and therefore not found
            if (authUserDoc == null) return null;
            // Create jackson object mapper
            ObjectMapper mapper = new ObjectMapper();
            // Converting the collection into a Json document and providing Jackson with the class
            // Allows Jackson to read the values and map them to the corresponding object.
            AppUser authUser = mapper.readValue(authUserDoc.toJson(), AppUser.class);
            // Handling the ID set by mongodb
            authUser.setId(authUserDoc.get("_id").toString());
            // Have to manually pull this since booleans are being cast to an object that isn't a Boolean
            authUser.setFaculty((boolean)authUserDoc.get("isFaculty"));
            return authUser;

        } catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.", jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    public AppUser findUserByUsername(String username) {
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new document with provided values to query database
            Document queryDoc = new Document("username", username);
            // Search the database for an instance of a collection with the matching values
            Document authUserDoc = usersCollection.find(queryDoc).first();
            // Return null if the values were not propagated and therefore not found
            if (authUserDoc == null) return null;
            // Create jackson object mapper
            ObjectMapper mapper = new ObjectMapper();
            // Converting the collection into a Json document and providing Jackson with the class
            // Allows Jackson to read the values and map them to the corresponding object.
            AppUser authUser = mapper.readValue(authUserDoc.toJson(), AppUser.class);
            // Handling the ID set by mongodb
            authUser.setId(authUserDoc.get("_id").toString());
            return authUser;

        }catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.", jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }


    public AppUser findUserByEmail(String email) {
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new document with provided values to query database
            Document queryDoc = new Document("email", email);
            // Search the database for an instance of a collection with the matching values
            Document authUserDoc = usersCollection.find(queryDoc).first();
            // Return null if the values were not propagated and therefore not found
            if (authUserDoc == null)
                return null;
            // Create jackson object mapper
            ObjectMapper mapper = new ObjectMapper();
            // Converting the collection into a Json document and providing Jackson with the class
            // Allows Jackson to read the values and map them to the corresponding object.
            AppUser authUser = mapper.readValue(authUserDoc.toJson(), AppUser.class);
            // Handling the ID set by mongodb
            authUser.setId(authUserDoc.get("_id").toString());
            return authUser;

        }catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.", jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }


    @Override
    public AppUser findByID(int id) {
        return null;
    }

    @Override
    public AppUser save(AppUser newUser) {


        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new user document with provided values
            Document newUserDoc = new Document("firstName", newUser.getFirstName())
                    .append("lastName", newUser.getLastName())
                    .append("email", newUser.getEmail())
                    .append("username", newUser.getUsername())
                    .append("password", newUser.getPassword())
                    .append("isFaculty", newUser.isFaculty());
            //Insert the new user document into the database
            usersCollection.insertOne(newUserDoc);
            //Set the user's ID to the ID generated by the database.
            newUser.setId(newUserDoc.get("_id").toString());

            return newUser;

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    @Override
    public boolean update(AppUser updatedResource) {
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }
}
