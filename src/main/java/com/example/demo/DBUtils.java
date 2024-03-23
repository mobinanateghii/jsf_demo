package com.example.demo;

import com.example.demo.dto.Student;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static DBUtils instance;
    private static DataSource dataSource;
    /*try to get this object in tomcat*/
    private String jndiName = "java:comp/env/jdbc/demo_jsf";/*its java naming directory interface*/

    /**
     * this is Singleton pattern
     * means just create object once in whole of our projects
     */
    public static DBUtils getInstance() throws NamingException {
        if(instance == null){
           instance = new DBUtils();
        }
        return instance;
    }

    private DBUtils() throws NamingException {
        dataSource = this.gateDataSource();
    }

    /**
     * lookUp connection pool that was created by tomcat
     * @return
     */
    private DataSource gateDataSource() throws NamingException {
        Context context = new InitialContext();
        return (DataSource) context.lookup(jndiName);
    }



    public List<Student> getStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();

        Connection myConn;
        Statement myStmt;
        ResultSet myRs;

        myConn = dataSource.getConnection();
        String sql = "select * from students order by first_name";
        myStmt = myConn.createStatement();
        myRs = myStmt.executeQuery(sql);

        /*Process result set*/
        while (myRs.next()) {
            studentList.add(new Student(myRs.getInt("id"), myRs.getString("first_Name"), myRs.getString("last_Name"), myRs.getString("email")));
        }
        return studentList;
    }

    public Student getStudent(int studentId) throws SQLException{
        Connection myConn = null;
        PreparedStatement myStmt = null;/*NOTE*/
        ResultSet myRs = null;

        try {
            myConn = dataSource.getConnection();
            String sql = "select * from students where id=?";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1, studentId);

            /**here we want to get response So we get response of execute query **/
            myRs = myStmt.executeQuery();

            if (myRs.next()) {
                return new Student(myRs.getInt("id"), myRs.getString("first_Name"), myRs.getString("last_Name"), myRs.getString("email"));
            }else {
                throw new IllegalArgumentException("could not find student id: "+ studentId);
            }
        }finally {
            myConn.close();
            myStmt.close();
            myRs.close();
        }
    }


    public void addStudent(Student student) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;/*NOTE*/

        try {
            myConn = dataSource.getConnection();
            String sql = "insert into students (first_name, last_name, email) values (?, ?, ?)";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1 , student.getFirstName());
            myStmt.setString(2 , student.getLastName());
            myStmt.setString(3 , student.getEmail());

            /**here we don't have any response So we just execute query**/
            myStmt.execute();
        }finally {
            myConn.close();
            myStmt.close();
        }
    }

    public void updateStudent(Student student) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;/*NOTE*/

        try {
            myConn = dataSource.getConnection();
            String sql = "update students set first_name =? , last_name=? , email=? where id=?";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setString(1 , student.getFirstName());
            myStmt.setString(2 , student.getLastName());
            myStmt.setString(3 , student.getEmail());
            myStmt.setInt(4 , student.getId());

            /**here we don't have any response So we just execute query**/
            myStmt.execute();
        }finally {
            myConn.close();
            myStmt.close();
        }
    }

    public void deleteStudent(Integer studentId) throws SQLException {
        Connection myConn = null;
        PreparedStatement myStmt = null;/*NOTE*/

        try {
            myConn = dataSource.getConnection();
            String sql = "delete from students where id=?";
            myStmt = myConn.prepareStatement(sql);

            myStmt.setInt(1 , studentId);

            /**here we don't have any response So we just execute query**/
            myStmt.execute();
        }finally {
            myConn.close();
            myStmt.close();
        }
    }
}
