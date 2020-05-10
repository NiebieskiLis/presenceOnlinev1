package database;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * A class that generate data from JSON file generated from mockaroo.com
 * And loads them to the DB for all tables
 * @version 09/05/2020/v1
 * @author Aleksandra Rezetka
 */
public class CreateData  {
    private static String dbURL = "jdbc:derby://localhost:1527/BazaPracownikow;create=true";
    private static Connection conn = null;
    private static Statement stmt = null;


    /**
     * It is a class that adds data to the department table with the connection of entity manager
     * @param filename - a path where json file with data exists
     * @param entityManager - entity manager that creates connection
     */
    public void createDepartments(String filename,  EntityManager entityManager){
        String line = null;
        JSONObject obj;
        ReadWriteLock myLock = new ReentrantReadWriteLock();
        myLock.readLock().lock();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            while((line = br.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                String department = (String) obj.get("Department");
                Department ftw = new Department(department);
                EntityTransaction tx = entityManager.getTransaction();
                tx.begin();
                entityManager.persist(ftw);
                tx.commit();

            }
        }catch(FileNotFoundException f)
        {
            f.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }finally {
            myLock.readLock().unlock();
        }

    }

    /**This method creates a list of full-time workers from the json file while using JSONPARSER and JSONObject
     * It also takes the list of department by sql statement
     * @param filename it takes path to the json file with data
     * @param entityManager
     */
    public void createFullTimeWorker(String filename, EntityManager entityManager){
        List<Department> departments;
        String queryString = "SELECT p FROM Department p";
        Query query = entityManager.createQuery(queryString);
        departments = query.getResultList();

        String line = null;
        String password;
        JSONObject obj;
        ReadWriteLock myLock = new ReentrantReadWriteLock();
        myLock.readLock().lock();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            while((line = br.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                String name = (String) obj.get("name");
                String surname = (String) obj.get("surname");
                password = (String) obj.get("password");
                Department dep = departments.get(Integer.valueOf(obj.get("departmentID").toString())-1);
                int paidLeave = Integer.valueOf(obj.get("paidLeave").toString());
                int childCareLeave = Integer.valueOf(obj.get("childCareLeave").toString());
                boolean multisport = ((boolean) obj.get("multisport"));
                boolean healthcare = ((boolean) obj.get("healtcareLeave"));
                int cashPerHour = Integer.valueOf(obj.get("cashPerHour").toString());
                FullTimeWorker ftw = new FullTimeWorker(password ,name ,surname,cashPerHour,paidLeave,multisport,healthcare,childCareLeave,dep);
                EntityTransaction tx = entityManager.getTransaction();
                tx.begin();
                entityManager.persist(ftw);
                tx.commit();
            }
            System.out.println("Added all of the workers to DB");
        }catch(FileNotFoundException f)
        {
            f.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }finally {
            myLock.readLock().unlock();
        }

    }

    /**This method creates a list of full-time workers from the json file while using JSONPARSER and JSONObject
     * It also takes the list of department and full time workers by sql statement
     * @param filename
     * @param entityManager
     */
    public void createPartTimeWorker(String filename , EntityManager entityManager ){
        String line = null;
        List<Department> departments ;
        List<FullTimeWorker> fulltime ;
        String queryString = "SELECT p FROM Department p";
        Query query = entityManager.createQuery(queryString);
        departments = query.getResultList();
        String queryString2 = "SELECT p FROM FullTimeWorker p";
        Query query2 = entityManager.createQuery(queryString2);
        fulltime = query2.getResultList();
        String password;
        JSONObject obj;
        ReadWriteLock myLock = new ReentrantReadWriteLock();
        myLock.readLock().lock();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            while((line = br.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                String name = (String) obj.get("name");
                String surname = (String) obj.get("surname");
                password = (String) obj.get("password");
                int cash = Integer.valueOf(obj.get("cashPerHour").toString());

                int min = Integer.valueOf(obj.get("minNumberOfHours").toString());
                //PartTimeWorker.Department department = ((PartTimeWorker.Department) obj.get("department"));
                FullTimeWorker supervisor = fulltime.get(Integer.valueOf(obj.get("supervisorID").toString()));
                int year = Integer.valueOf(obj.get("year").toString());
                int month = Integer.valueOf(obj.get("month").toString());
                int day = Integer.valueOf(obj.get("day").toString());
                Department department=departments.get(Integer.valueOf(obj.get("department").toString()));
                PartTimeWorker ftw = new PartTimeWorker(password, name, surname, cash, min, department, supervisor, year, month, day);
                EntityTransaction tx = entityManager.getTransaction();
                tx.begin();
                entityManager.persist(ftw);
                tx.commit();
            }
        }catch(FileNotFoundException f)
        {
            f.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }finally {
            myLock.readLock().unlock();
        }
    }
}
