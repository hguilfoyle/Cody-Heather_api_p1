package com.revature.ncu.datasources.repositories;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.ncu.datasources.documents.Course;
import com.revature.ncu.datasources.documents.UserCourses;
import com.revature.ncu.datasources.utils.MongoClientFactory;
import com.revature.ncu.util.exceptions.DataSourceException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

// Repository for performing CRUD operations on the Mongo usercourses collection
// Creates JSON files for injection into Mongo

public class CourseRepository implements CrudRepository<Course> {

    private static final String DATABASE = "p0";
    private static final String COLLECTION = "courses";

    private final Logger logger = LoggerFactory.getLogger(CourseRepository.class);


    public Course findCourseByName(String courseName) {
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new document with provided values to query database
            Document queryDoc = new Document("courseName", courseName);
            // Search the database for an instance of a collection with the matching values
            Document courseDoc = usersCollection.find(queryDoc).first();
            // Return null if the values were not propagated and therefore not found
            if (courseDoc == null) return null;
            // Create jackson object mapper
            ObjectMapper mapper = new ObjectMapper();
            // Converting the collection into a Json document and providing Jackson with the class
            // Allows Jackson to read the values and map them to the corresponding object.
            Course course = mapper.readValue(courseDoc.toJson(), Course.class);
            // Handling the ID set by mongodb
            course.setId(courseDoc.get("_id").toString());
//            course.setOpen((boolean)courseDoc.get("isOpen"));
            return course;

        } catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.",jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    public Course findCourseByAbbreviation(String courseAbv) {
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Create new document with provided values to query database
            Document queryDoc = new Document("courseAbbreviation", courseAbv);
            // Search the database for an instance of a collection with the matching values
            Document courseDoc = usersCollection.find(queryDoc).first();
            // Return null if the values were not propagated and therefore not found
            if (courseDoc == null)
            {
                return null;
            }
            // Create jackson object mapper
            ObjectMapper mapper = new ObjectMapper();
            // Converting the collection into a Json document and providing Jackson with the class
            // Allows Jackson to read the values and map them to the corresponding object.
            Course course = mapper.readValue(courseDoc.toJson(), Course.class);
            // Handling the ID set by mongodb
            course.setId(courseDoc.get("_id").toString());
//            course.setOpen((boolean)courseDoc.get("isOpen"));
            return course;

        } catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.", jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }


    public List<Course> retrieveOpenCourses() {
            return null;
    }

    public void updatingCourseName(Course original,String newName){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> coursesCollection = p0Db.getCollection(COLLECTION);

            // Append $set to "courseName" : newName
            Document updateDoc = new Document("courseName", newName);
            Document appendDoc = new Document("$set",updateDoc);
            // search for "courseName" : original courseName
            Document searchDoc = new Document("courseName",original.getCourseName());

            //
            coursesCollection.updateOne(searchDoc,appendDoc);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    public void updatingCourseAbv(Course original, String newAbv){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> coursesCollection = p0Db.getCollection(COLLECTION);

            Document updateDoc = new Document("courseAbbreviation", newAbv);
            Document appendDoc = new Document("$set",updateDoc);
            Document searchDoc = new Document("courseAbbreviation", original.getCourseAbbreviation());

            coursesCollection.updateOne(searchDoc,appendDoc);

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }


    public void updatingCourseDesc(Course original, String newDesc){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> coursesCollection = p0Db.getCollection(COLLECTION);

            Document updateDoc = new Document("courseDetail", newDesc);
            Document appendDoc = new Document("$set",updateDoc);
            Document searchDoc = new Document("courseAbbreviation",original.getCourseAbbreviation());

            coursesCollection.updateOne(searchDoc,appendDoc);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }



    // Remove a course from the database
    public void removeCourse(Course course){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> coursesCollection = p0Db.getCollection(COLLECTION);

            Document queryDoc = new Document("courseAbbreviation", course.getCourseAbbreviation());

            coursesCollection.findOneAndDelete(queryDoc);
        }catch (Exception e){
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }

    }

    @Override
    public List<Course> findAll(){

        return null;

    }

    @Override
    public Course findById(String id) {
        return null;
    }

    //Save the course to the database
    @Override
    public Course save(Course newCourse) {
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> courseCollection = p0Db.getCollection(COLLECTION);

            // Create new course document with provided values
            Document newCourserDoc = new Document("courseName", newCourse.getCourseName())
                    .append("courseAbbreviation", newCourse.getCourseAbbreviation())
                    .append("courseDetail", newCourse.getCourseDetail());


            //Insert the new course document into the database
            courseCollection.insertOne(newCourserDoc);

            //Set the course ID to the ID generated by the database.
            newCourse.setId(newCourserDoc.get("_id").toString());

            return newCourse;

        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
    }

    @Override
    public boolean update(Course updatedResource) {
        return false;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

}
