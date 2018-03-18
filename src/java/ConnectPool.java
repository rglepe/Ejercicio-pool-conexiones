/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.naming.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.*;

/**
 *
 * @author Raul
 */
@WebServlet(urlPatterns = {"/pool"})
public class ConnectPool extends HttpServlet {

    private DataSource dataSource;
    private Connection connection;
    private Statement statement;

    public void init() throws ServletException {
    
        try{
            
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource)envContext.lookup("jdbc/testdb");
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        try(PrintWriter out = response.getWriter()){
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            String query = "select * from usuario";
            ResultSet rs = statement.executeQuery(query);
            
            while(rs.next()){
                out.println(rs.getString("usuario")+" "+rs.getString("password"));
            }
            
                    
            
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
