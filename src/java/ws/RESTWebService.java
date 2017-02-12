/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import dao.Student;
import dao.StudentDAO;
import dao.StudentList;
import dao.StudentListDAO;
import java.util.Collection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author ludovic
 */
@Path("api")
public class RESTWebService {
    private StudentListDAO studentListDao;
    private StudentDAO studentDao;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RESTWebService
     */
    public RESTWebService() {
        //Persistence.generateSchema("UniversityPU", null);
        studentListDao = new StudentListDAO();
        studentDao = new StudentDAO();
    }

    /**
     * Retrieves representation of an instance of ws.RESTWebService
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    public String getText() {
        return "Service REST University";
    }
    
    @GET
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public StudentList getStudentList(@PathParam("id") int id) {
        return studentListDao.findById(id);
    }
    
    @GET
    @Path("/list/{list-id}/student/{student-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent(@PathParam("list-id") int listId, @PathParam("student-list") int studentId) {
        //return studentListDao.findById(listId).getStudent(studentId);
        
        return studentDao.findById(studentId);
    }
    
    @GET
    @Path("/list/{id}/students")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Student> getStudents(@PathParam("id") int id) {
        return studentListDao.findById(id).getStudents();
    }
    
    @GET
    @Path("/lists")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<StudentList> getStudentLists() {
        return studentListDao.findAll();
    }
    
    @POST
    @Path("/list")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createStudentList(StudentList studentList) {
        return Integer.toString(studentListDao.create(studentList));
    }
    
    @POST
    @Path("/list/{id}/student")
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_JSON)
    public String createStudent(@PathParam("id") int id, Student student) {
        /*StudentList list = studentListDao.findById(id);
        student.setStudentList(list);
        list.addStudent(student);
        
        return Integer.toString(studentDao.create(student));*/
        
        return Integer.toString(studentListDao.addStudent(id, student));
    }
    
    @PUT
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StudentList updateStudentList(@PathParam("id") int id, StudentList studentList) {
        return studentListDao.update(id, studentList);
    }
    
    @PUT
    @Path("/list/{list-id}/student/{student-id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Student updateStudent(@PathParam("list-id") int listId, @PathParam("student-id") int studentId, Student student) {
        //return studentListDao.findById(listId).editStudent(studentId, student);
        
        return studentDao.update(studentId, student);
    }
    
    @DELETE
    @Path("/list/{id}")
    public void deleteStudentList(@PathParam("id") int id) {
        studentListDao.delete(id);
    }
    
    @DELETE
    @Path("/list/{list-id}/student/{student-id}")
    public void deleteStudent(@PathParam("list-id") int listId, @PathParam("student-id") int studentId) {
        //studentListDao.findById(listId).removeStudent(studentId);
        
        studentDao.delete(studentId);
    }
}
