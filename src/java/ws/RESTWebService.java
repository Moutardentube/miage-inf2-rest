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
import java.net.URI;
import java.util.List;
import javax.persistence.NoResultException;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getStudentList(@PathParam("id") int id) {
        try {
            return Response.ok(studentListDao.findById(id)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/list/{list-id}/student/{student-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("list-id") int listId, @PathParam("student-list") int studentId) {
        //return studentListDao.findById(listId).getStudent(studentId);
        
        try {
            return Response.ok(studentDao.findById(studentId)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/list/{id}/student")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents(@PathParam("id") int id) {
        try {
            return Response.ok(new GenericEntity<List<Student>>(studentListDao.findById(id).getStudents()){}).build();
        } catch (NoResultException | NullPointerException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentLists() {
        try {
            return Response.ok(new GenericEntity<List<StudentList>>(studentListDao.findAll()){}).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("/list")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStudentList(StudentList studentList) {
        try {
            String id = Integer.toString(studentListDao.create(studentList));
            URI location = context.getRequestUriBuilder().path(id).build();

            return Response.created(location).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @POST
    @Path("/list/{list-id}/student")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createStudent(@PathParam("list-id") int listId, Student student) {
        /*StudentList list = studentListDao.findById(id);
        student.setStudentList(list);
        list.addStudent(student);
        
        return Integer.toString(studentDao.create(student));*/
        
        try {
            String id = Integer.toString(studentListDao.addStudent(listId, student));
            URI location = context.getRequestUriBuilder().path(id).build();

            return Response.created(location).build();
        } catch (NoResultException | NullPointerException e) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudentList(@PathParam("id") int id, StudentList studentList) {
        try {
            return Response.ok(studentListDao.update(id, studentList)).build();
        } catch (NoResultException | NullPointerException e) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @PUT
    @Path("/list/{list-id}/student/{student-id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("list-id") int listId, @PathParam("student-id") int studentId, Student student) {
        //return studentListDao.findById(listId).editStudent(studentId, student);
        
        if (studentListDao.findById(listId) == null) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        
        try {
            return Response.ok(studentDao.update(studentId, student)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("/list/{id}")
    public Response deleteStudentList(@PathParam("id") int id) {
        try {
            studentListDao.delete(id);
                    
            return Response.noContent().build();
        }  catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
    
    @DELETE
    @Path("/list/{list-id}/student/{student-id}")
    public Response deleteStudent(@PathParam("list-id") int listId, @PathParam("student-id") int studentId) {
        //studentListDao.findById(listId).removeStudent(studentId);
        
        if (studentListDao.findById(listId) == null) {
            return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        
        try {
            studentDao.delete(studentId);
            
            return Response.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
