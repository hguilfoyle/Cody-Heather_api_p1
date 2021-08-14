package com.revature.ncu.datasources.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.revature.ncu.util.exceptions.DataSourceException;
import com.revature.ncu.datasources.documents.AppUser;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


//Repository for performing CRUD operations on the Mongo user collection.
public class UserRepository implements CrudRepository<AppUser>{

    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    private final MongoCollection<AppUser> usersCollection;

    // Get connection, access database, and create collection.
    public UserRepository(MongoClient mongoClient) {
        this.usersCollection = mongoClient.getDatabase("p1").getCollection("users", AppUser.class);
    }

    public AppUser findUserByCredentials(String username, String encryptedPassword) {

        try {
            // Create a Query JSON to
            Document queryDoc = new Document("username", username).append("password", encryptedPassword);
            return usersCollection.find(queryDoc).first();
        }
            catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    public AppUser findUserByUsername(String username) {

        try {
            return usersCollection.find(new Document("username", username)).first();
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    public AppUser findUserByEmail(String email) {
        try {
            return usersCollection.find(new Document("email", email)).first();
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    @Override
    public List<AppUser> findAll() {

        List<AppUser> users = new ArrayList<>();

        try {
            usersCollection.find().into(users);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
        return users;
    }


    @Override
    public AppUser findById(String id) {

        try {

            Document queryDoc = new Document("_id", id);
            return usersCollection.find(queryDoc).first();

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    @Override
    public AppUser save(AppUser newUser) {
        try {
            newUser.setId(new ObjectId().toString());
            usersCollection.insertOne(newUser);
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
    public boolean deleteById(String id) {
        return false;
    }
}
