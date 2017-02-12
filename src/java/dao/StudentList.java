/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ludovic
 */
@Entity(name="StudentList")
public class StudentList implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable = false, unique=true)
    private int id;
    
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="studentList", cascade=CascadeType.ALL)
    private List<Student> students;
    
    public StudentList() {
        
    }
    
    public StudentList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Student> getStudents() {
        return this.students;
    }
    
    public void addStudent(Student student) {
        this.students.add(student);
    }
    
    public void removeStudent(Student student) {
        this.students.remove(student);
    }
}
