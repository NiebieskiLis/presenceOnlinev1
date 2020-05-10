package database;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Class which is responsible for loading the data to the databse and performing some operations
 * @author Maciej Adamczyk
 * @version 9.05.2020
 */
public class LoadingData {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");

    /**
     * Function with the menu, which allows user to log in, and to perform some operations
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        //data loading
        Scanner scan = new Scanner(System.in);
        System.out.print("Hello World\n");
        loadData();
        //System.out.print("Bye World");

        //Conecting to the database
        String dbURL = "jdbc:derby://localhost:1527/BazaPracownikow;create=true";
        Connection conn = null;
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            //Get a connection
            conn = DriverManager.getConnection(dbURL);
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //logging in
        boolean logging = false;
        boolean is_fulltime = false;
        int is_accountant = 0;
        while (logging == false) {
            System.out.println("Login: \n");
            String Login = scan.nextLine();
            System.out.println("Password \n");
            String Password = scan.nextLine();
            logging = loggin_in(Login, Password, entityManager);
            /*
            is_fulltime = is_fulltime(Login, conn);
            if (is_fulltime == true)
                is_accountant = if_accountant_ft(Login, conn);
            else
                is_accountant = if_accountant_pt(Login, conn);

             */
        }
        /*
        System.out.println("Aby rozpoczac sesje, kliknij 1: \n");
        String sesja = scan.nextLine();


        if(sesja.equals("1")) {
            if(is_accountant == 1) {
                String Operation_Type = null;
                while (sesja.equals("1")) {
                    System.out.println("What would you like to do? Retrieve/Update/Delete/Close session \n");
                    Operation_Type = scan.nextLine();
                    switch (Operation_Type) {
                        case "Retrieve":
                            retrieve_data(conn);
                            break;
                        case "Update":
                            update_data(conn);
                            break;
                        case "Delete":
                            delete_data(conn);
                            break;
                        case "Close":
                            sesja = "2";
                            break;
                    }
                }
            }
        }

         */
        entityManager.close();
    }

    /**
     * Function which provides loading data to the databse
     */
    private static void loadData() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //  entityManager.getTransaction().begin();
        CreateData cr = new CreateData();
        cr.createDepartments("Data/Department.json", entityManager);
        System.out.println("Poza departmentem");
        cr.createFullTimeWorker("Data/workersFT.json", entityManager);
        System.out.println("Poza FTW");

        cr.createPartTimeWorker("Data/workersPT.json", entityManager);
        entityManager.close();
    }


    /**
     * Function which provides loggin features
     * @param login
     * @param log_password
     * @param conn
     * @return
     * @throws SQLException
     */
    private static boolean loggin_in(String login, String log_password, EntityManager em){
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        Query query = em.createQuery(queryString);
        query.setParameter("login", "login");
        List<FullTimeWorker> products = query.getResultList();
        if (products.size() > 0) {
            if(products.get(0).getPassword().equals(log_password)) {
                return true;
            }
            else
                return false;
        }
        else {
            String queryString2 = "SELECT p FROM PartTimeWorker p WHERE p.login LIKE :login";
            Query query2 = em.createQuery(queryString2);
            query2.setParameter("login", "login");
            List<PartTimeWorker> products2 = query2.getResultList();
            if (products2.size() > 0) {
                if(products2.get(0).getPassword().equals(log_password)) {
                    return true;
                }
                else
                    return false;
            }
            else {
                return false;
            }

        }

    }

    /**
     * Function which checks if full time worker is an accountant
     * @param login
     * @param conn
     * @return
     * @throws SQLException
     */
    private static int if_accountant_ft(String login, Connection conn) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Statement stmt = conn.createStatement();
        String query = "SELECT department FROM fulltimeworker WHERE login LIKE " + login;
        ResultSet result = stmt.executeQuery(query);
        if (result.getString("department").equals("6"))
            return 1;
        else
            return 0;
    }

    private static boolean is_fulltime(String login, Connection conn) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Statement stmt = conn.createStatement();
        String query = "SELECT department FROM fulltimeworker WHERE login LIKE " + login;
        ResultSet result = stmt.executeQuery(query);
        if (result.getString("department").equals("6"))
            return true;
        else
            return false;
    }


    /**
     * Function which checks if part time worker is an accountant
     * @param login
     * @param conn
     * @return
     * @throws SQLException
     */
    private static int if_accountant_pt(String login, Connection conn) throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Statement stmt = conn.createStatement();
        String query = "SELECT department FROM fulltimeworker WHERE surname LIKE " + login;
        ResultSet result = stmt.executeQuery(query);
        if (result.getString("department").equals("6"))
            return 1;
        else
            return 0;
    }

    /**
     * Function which allows updating a record in the database
     * @param conn
     */
    private static void update_data(Connection conn) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Which table you want to update: \n");
        String table = scan.nextLine();
        System.out.println("What is the column?: \n");
        String column = scan.nextLine();
        System.out.println("What is the new value?: \n");
        String value = scan.nextLine();
        System.out.println("What is the condition?: \n");
        String condition = scan.nextLine();

        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("UPDATE " + table + " SET " + column + " = " + value + " WHERE " + condition);
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }

    /**
     * Function which allows deleting a record from the databse
     * @param conn
     */
    private static void delete_data(Connection conn) {
        Scanner scan = new Scanner(System.in);
        System.out.println("From which table you want to delete: \n");
        String table = scan.nextLine();
        System.out.println("What is the condition?: \n");
        String condition = scan.nextLine();
        try {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("DELETE FROM " + table + " WHERE " + condition);
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }

    /**
     * Function which allows selecting data from the database
     * @param conn
     */
    private static void retrieve_data(Connection conn) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Which column you want to select: \n");
        String column = scan.nextLine();
        System.out.println("In what table?: \n");
        String table = scan.nextLine();
        System.out.println("Is there any condition? If no, press None, else enter a condition: \n");
        String condition = scan.nextLine();
        try {
            Statement stmt = conn.createStatement();
            if (condition.equals("None")) {
                ResultSet result = stmt.executeQuery("SELECT " + column + " FROM " + table);
            }
            else {
                ResultSet result = stmt.executeQuery("SELECT " + column + " FROM " + table + " WHERE " + condition);
            }
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }


}