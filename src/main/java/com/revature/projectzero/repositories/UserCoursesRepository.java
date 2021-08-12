package com.revature.projectzero.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.revature.projectzero.documents.UserCourses;
import com.revature.projectzero.util.MongoClientFactory;
import com.revature.projectzero.util.exceptions.DataSourceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.List;

// Repository for performing CRUD operations on the Mongo usercourses collection

public class UserCoursesRepository implements CrudRepository<UserCourses> {

    private static final String DATABASE = "p0";
    private static final String COLLECTION = "usercourses";

    private final Logger logger = LogManager.getLogger(UserCoursesRepository.class);

    public List<String> findRegisteredCoursesByUsername(String username){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            Document queryDoc = new Document("username", username);
            Document userCourseDoc = usersCollection.find(queryDoc).first();
            if (userCourseDoc == null) return null;
            ObjectMapper mapper = new ObjectMapper();
            UserCourses userCourses = mapper.readValue(userCourseDoc.toJson(), UserCourses.class);
            userCourses.setId(userCourseDoc.get("_id").toString());

            return userCourses.getCourses();
        }
        catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.", jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public void joinCourse(String course,String username){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> userCoursesCollection = p0Db.getCollection(COLLECTION);
            Document updateDoc = new Document("courses", course);
            Document appendDoc = new Document("$push",updateDoc);
            Document searchDoc = new Document("username",username);
            userCoursesCollection.updateOne(searchDoc,appendDoc);

        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public void removeCourseFromUserList(String course, String username){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> userCoursesCollection = p0Db.getCollection(COLLECTION);

            Document removeDoc = new Document("courses", course);
            Document appendDoc = new Document("$pull",removeDoc);
            Document searchDoc = new Document("username",username);
            userCoursesCollection.updateOne(searchDoc,appendDoc);

        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public void updateCourseNameInAllUserLists(String originalName, String newName){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> userCoursesCollection = p0Db.getCollection(COLLECTION);

            //push the new name
            Document updateDoc = new Document("courses", newName);
            Document pushDoc = new Document("$push",updateDoc);
            Document searchDoc = new Document("courses", originalName);
            userCoursesCollection.updateMany(searchDoc, pushDoc);

            //pull the old name
            Document removalDoc = new Document("courses", originalName);
            Document pullDoc = new Document("$pull",removalDoc);
            userCoursesCollection.updateMany(searchDoc,pullDoc);


        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public void removeCourseFromAllUserLists(String course){

        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> userCoursesCollection = p0Db.getCollection(COLLECTION);

            Document updateDoc = new Document("courses", course);
            Document appendDoc = new Document("$pull",updateDoc);
            Document searchDoc = new Document("courses", course);
            userCoursesCollection.updateMany(searchDoc,appendDoc);

        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }


    @Override
    public UserCourses findByID(int id) {
        return null;
    }

    //initialize the user's courseList
    @Override
    public UserCourses save(UserCourses newCourseList) {
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new document with provided values
            Document newListDoc = new Document("username", newCourseList.getUsername())
                    .append("courses", newCourseList.getCourses());
            //Insert the new user document into the database
            usersCollection.insertOne(newListDoc);
            //Set the ID to the ID generated by the database.
            newCourseList.setId(newListDoc.get("_id").toString());

            return newCourseList;

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    @Override
    public boolean update(UserCourses updatedResource) {
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        return false;
    }


}
