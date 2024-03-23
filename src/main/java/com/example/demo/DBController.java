package com.example.demo;

import com.example.demo.dto.Student;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * should be Session scoped because it's different for per user
 */
@ManagedBean
@SessionScoped
public class DBController {
    private List<Student> studentList;
    private final DBUtils dbUtils;
    private final Logger logger = Logger.getLogger(getClass().getName());

    public DBController() throws Exception{
        studentList = new ArrayList<>();
        dbUtils = DBUtils.getInstance();
    }


    public List<Student> getStudentList(){
        return studentList;
    }

    public void loadStudents(){
        logger.info("Student Loading!");
        studentList.clear();

        try {
            studentList = dbUtils.getStudents();
        }catch (Exception e){
            logger.log(Level.SEVERE, "Error loading studentList ", e);
            this.addErrorMessage(e);
        }
    }


    public String loadStudent(int studentId, String redirectUrl){
        logger.info("loading Student: " + studentId);

        try {
            Student student = dbUtils.getStudent(studentId);

            /** put in request attribute ... so we can use it on form page*/
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> requestMap = externalContext.getRequestMap();
            requestMap.put("student", student);

        }catch (Exception e){
            logger.log(Level.SEVERE, "Error loading student :" + studentId, e);
            this.addErrorMessage(e);
            return null;
        }
        return redirectUrl;
    }

    public String addStudent(Student student, String redirectUrl){
        logger.info("Student adding!");

        try {
            dbUtils.addStudent(student);
        }catch (Exception e){
            logger.log(Level.SEVERE, "Error adding student ", e);
            this.addErrorMessage(e);
        }

        return redirectUrl;
    }

    public String updateStudent(Student student, String redirectUrl){
        logger.info("Student adding!");

        try {
            dbUtils.updateStudent(student);
        }catch (Exception e){
            logger.log(Level.SEVERE, "Error updating student id: " + student.getId(), e);
            this.addErrorMessage(e);
        }

        return redirectUrl;
    }

    public void deleteStudent(Integer studentId){
        logger.info("Student deleting!");

        try {
            dbUtils.deleteStudent(studentId);
        }catch (Exception e){
            logger.log(Level.SEVERE, "Error deleting student id: " + studentId, e);
            this.addErrorMessage(e);
        }
    }

    private void addErrorMessage(Exception e) {
        FacesMessage message = new FacesMessage("Error: " + e.getMessage());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }


}
