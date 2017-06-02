package Application.Database;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Sohel on 5/28/2017.
 */
public class DatabaseHandler {

    private static final String DB_URL="jdbc:mysql://localhost:3306/rice_mill";
    private static final String DB_USER="root";
    private static final String DB_PASSWORD="root";

    private static DatabaseHandler handler = null;

    private Connection conn = null;
    private static Statement stmt = null;



    private DatabaseHandler(){
        this.conn =getConnection();
    }

    public static DatabaseHandler getInstance(){
        if(handler==null){
            handler = new DatabaseHandler();
        }

        return handler;

    }

    public  Connection getConnection(){
        Connection myConn = null;
        try {
            myConn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return myConn;
    }

    public void closeConnection() throws SQLException {
        if(conn!=null){
            conn.close();
        }
    }

    public ResultSet executeQuery(String query){
        ResultSet resultSet = null;

        try{
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultSet;
    }

    public boolean execAction(String query){
        boolean exec = false;
        try{
            stmt = conn.createStatement();
            stmt.execute(query);
            exec= true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error: "+e.getMessage(),"Error Occured",JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception Occur in Execute Query");
            e.printStackTrace();

        }
        return exec;
    }

}
