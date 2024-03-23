package com.example.demo;

import lombok.Data;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean
@ApplicationScoped
@Data
/**
 * application scoped means that only one bean is generated for whole application and shared to all users!
 *
 * default scope is RequestScope! this scope used for formData and so on! it's commonly have short lifeCycle
 */
public class ApplicationBean {
    private int counter;
    private List<Map<String,String>> studentList = new ArrayList<>();

    public ApplicationBean(){
        this.loadStudentList();
    }

    public String increment(){
        this.counter += 1;
        return "sample";
    }

    public void loadStudentList(){
        studentList.add(new HashMap<String,String>(){{
            put("firstName", "john");
            put("lastName", "doe");
            put("email", "Doe@gmail.com");
        }});

        studentList.add(new HashMap<String,String>(){{
            put("firstName", "sara");
            put("lastName", "maxi");
            put("email", "maxi@gmail.com");
        }});
    }

    /**
     * should use ApplicationScoped bean because we want to show this data from DB and want to be shared in all of our project!
     * loaded and manipulate once!
     */
    public List<Map<String,String>> getStudentList(){
        return this.studentList;
    }

}
