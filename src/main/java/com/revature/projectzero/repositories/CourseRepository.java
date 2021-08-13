package com.revature.projectzero.repositories;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.revature.projectzero.documents.Course;
import com.revature.projectzero.util.MongoClientFactory;
import com.revature.projectzero.util.exceptions.DataSourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

// Repository for performing CRUD operations on the Mongo usercourses collection
// Creates JSON files for injection into Mongo

public class CourseRepository implements CrudRepository<Course> {

    private static final String DATABASE = "p0";
    private static final String COLLECTION = "courses";

    private final Logger logger = LogManager.getLogger(CourseRepository.class);


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
            course.setOpen((boolean)courseDoc.get("isOpen"));
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
            course.setOpen((boolean)courseDoc.get("isOpen"));
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
        try {
            // ArrayList to hold courses
            List<Course> courses = new ArrayList<>();

            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> usersCollection = p0Db.getCollection(COLLECTION);

            // Retrieve classes that are open
            Document queryDoc = new Document("isOpen", true);
            // Retrieve an iterable document of all collections
            FindIterable<Document> openCoursesDoc = usersCollection.find(queryDoc);
            // Create jackson object mapper
            ObjectMapper mapper = new ObjectMapper();
            Course openCourse;

            for(Document a: openCoursesDoc){
                openCourse = mapper.readValue(a.toJson(), Course.class);
                openCourse.setId(a.get("_id").toString());
                openCourse.setOpen((boolean)a.get("isOpen"));
                courses.add(openCourse);
            }
            return courses;

        } catch (JsonMappingException jme) {
            logger.error("An exception occurred while mapping the document.", jme);
            throw new DataSourceException("An exception occurred while mapping the document.", jme);
        } catch (Exception e) {
            logger.error("An unexpected exception occurred.", e);
            throw new DataSourceException("An unexpected exception occurred.", e);
        }
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

    // if/else switch to set course to open or closed.
    public void openClose(Course course){
        try {
            // Get connection, access database, and access collection.
            MongoClient mongoClient = MongoClientFactory.getInstance().getConnection();
            MongoDatabase p0Db = mongoClient.getDatabase(DATABASE);
            MongoCollection<Document> coursesCollection = p0Db.getCollection(COLLECTION);

            if(course.isOpen()) {
                Document updateDoc = new Document("isOpen", false);
                Document appendDoc = new Document("$set", updateDoc);
                Document searchDoc = new Document("courseAbbreviation", course.getCourseAbbreviation());
                coursesCollection.updateOne(searchDoc, appendDoc);
            }else
            {
                Document updateDoc = new Document("isOpen", true);
                Document appendDoc = new Document("$set", updateDoc);
                Document searchDoc = new Document("courseAbbreviation", course.getCourseAbbreviation());
                coursesCollection.updateOne(searchDoc, appendDoc);
            }


        } catch (Exception e) {
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
    public Course findByID(int id) {
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
                    .append("courseDetail", newCourse.getCourseDetail())
                    .append("isOpen", newCourse.isOpen());

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
    public boolean deleteByID(int id) {
        return false;
    }

}
