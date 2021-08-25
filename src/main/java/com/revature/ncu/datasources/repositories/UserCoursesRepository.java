package com.revature.ncu.datasources.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import com.revature.ncu.datasources.documents.UserCourses;
import com.revature.ncu.util.exceptions.DataSourceException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

// Repository for performing CRUD operations on the Mongo usercourses collection
public class UserCoursesRepository implements CrudRepository<UserCourses> {

    private final Logger logger = LoggerFactory.getLogger(UserCoursesRepository.class);
    private final MongoCollection<UserCourses> userCoursesCollection;

    // Get connection, access database, and create collection.
    public UserCoursesRepository(MongoClient mongoClient) {
        this.userCoursesCollection = mongoClient.getDatabase("p1").getCollection("usercourses", UserCourses.class);
    }

    public List<String> findRegisteredCoursesByUsername(String username){
        try {
            Document queryDoc = new Document("username", username);
            return userCoursesCollection.find(queryDoc).first().getCourses();
        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }
    }

    public void joinCourse(String course,String username){
        try {
            // Search by username
            Document searchDoc = new Document("username",username);

            // Create $push command to push new course into array on database
            Document updateDoc = new Document("courses", course);
            Document appendDoc = new Document("$push",updateDoc);
            userCoursesCollection.updateOne(searchDoc,appendDoc);

        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }
    }

    public void removeCourseFromUserList(String username, String course){
        try {
            // Search by username
            Document searchDoc = new Document("username",username);

            // Create $pull command to pull course from array on database
            Document removeDoc = new Document("courses", course);
            Document appendDoc = new Document("$pull",removeDoc);
            userCoursesCollection.updateOne(searchDoc,appendDoc);

        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }
    }

    public void updateCourseAbvInAllUserLists(String originalAbv, String newAbv){
        try {
            // Search
            Document searchDoc = new Document("courses", originalAbv);

            // Push the new name
            Document updateDoc = new Document("courses", newAbv);
            Document pushDoc = new Document("$push",updateDoc);
            userCoursesCollection.updateMany(searchDoc, pushDoc);

            // Pull the old name
            Document pullDoc = new Document("$pull",searchDoc);
            userCoursesCollection.updateMany(searchDoc,pullDoc);
        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }
    }

    public void removeCourseFromAllUserLists(String course){

        try {
            // Search
            Document searchDoc = new Document("courses", course);

            // Pull the course from all lists
            Document updateDoc = new Document("courses", course);
            Document appendDoc = new Document("$pull",updateDoc);
            userCoursesCollection.updateMany(searchDoc,appendDoc);

        }
        catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }

    }

    // For Listing all users and their registered courses
    @Override
    public List<UserCourses> findAll(){

        List<UserCourses> userCourses = new ArrayList<>();

        try{
            userCoursesCollection.find().into(userCourses);
        } catch (Exception e){
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }
        return userCourses;
    }

    @Override
    public UserCourses findById(String id) {
        return null;
    }

    //initialize the user's courseList
    @Override
    public UserCourses save(UserCourses newCourseList) {
        try {
            // Generate ID and insert into database.
            newCourseList.setId(new ObjectId().toString());
            userCoursesCollection.insertOne(newCourseList);
            return newCourseList;

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException(e);
        }
    }

    @Override
    public boolean update(UserCourses updatedResource) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

}
