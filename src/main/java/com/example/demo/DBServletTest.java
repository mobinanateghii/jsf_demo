package com.example.demo;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/DBServletTest")
public class DBServletTest extends HttpServlet {

    @Resource(name = "jdbc/demo_jsf")
    private DataSource dataSource;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        Connection myConn;
        Statement myStmt;
        ResultSet myRs;

        try {
            myConn = dataSource.getConnection();
            String sql = "select * from students";
            myStmt = myConn.createStatement();
            myRs = myStmt.executeQuery(sql);

            while (myRs.next()){
                String email = myRs.getString("email");
                out.println(email);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
