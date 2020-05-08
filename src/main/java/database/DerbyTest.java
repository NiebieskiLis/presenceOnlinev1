package database;

import java.sql.*;

public class DerbyTest {

    private String dbURL = "jdbc:derby:database\\db-derby-10.15.2.0-lib\\lib\\BazaPracownikow;create=true";
    private String dbURL2 = "jdbc:derby://localhost:1527/BazaPracownikow;create=true";
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        DerbyTest dt = new DerbyTest();
        Connection c = dt.connectToDB();
    }

    protected Connection connectToDB() {
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("ClassNotFoundException - connectToDB(): " + e.getMessage());
        }
        try {

            connection = DriverManager.getConnection(dbURL);
            System.out.print("Connection was made \n ");
            return connection;

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Unable to connect to database due to  " + ex.getMessage());
            return null;
        }

    }
}