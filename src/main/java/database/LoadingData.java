package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import javax.persistence.*;
import java.sql.SQLException;



/**
 * Class which is responsible for loading the data to the databse and performing some operations
 * @author Maciej Adamczyk
 * @version 9.05.2020
 */
public class LoadingData {

    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");

    /** A main menu that exists show all of the possibilities that where described in first task
     * @author Aleksandra Rezetka
     * @param entityManager
     * @param logged
     * @param isAccountant
     * @param isFulltime
     */
    public static void mainMenu(EntityManager entityManager , Worker logged , int isAccountant , boolean isFulltime){
        System.out.println("Session is started: \n");
        String element = "";
        String sesja = "1";
         while (sesja.equals("1")) {
                System.out.println("What would you like to do? \n s - start shift \n e - end shift  \n c - close \n  a - add worker \n d- delete worker");
                try {
                    BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                    element = obj.readLine();
                } catch (IOException e) {
                    System.out.print("Unable to read character from file_type");
                }
                switch (element) {
                    case "s":
                        if(isFulltime)
                        {
                            //Transaction example
                            ShiftFTW s = new ShiftFTW(logged);
                            s.startShiftFTW();
                            EntityTransaction tx = entityManager.getTransaction();
                            tx.begin();
                            entityManager.persist(s);
                            tx.commit();
                        }
                        else
                        {
                            //A shift for part time worker
                            ShiftPTW p = new ShiftPTW(logged);
                            p.startShiftPTW();
                            EntityTransaction tx = entityManager.getTransaction();
                            tx.begin();
                            entityManager.persist(p);
                            tx.commit();
                        }
                        System.out.println("You just started your last shift !");
                        break;
                    case "e":
                        if(isFulltime)
                        {
                            //Query with parmeter
                            String queryString = "SELECT p FROM ShiftFTW p WHERE p.worker = :login AND p.shiftEnd is null AND p.shiftStart is not null ORDER BY p.shiftStart DESC ";
                            Query query = entityManager.createQuery(queryString);
                            query.setParameter("login", logged);
                            List<ShiftFTW> products = query.getResultList();
                            if (products.size()==1){
                                System.out.println("Unable to close shift - non of yours is open");
                            }else{
                                ShiftFTW s = entityManager.find(ShiftFTW.class,products.get(0).getID_FTWorker());
                                entityManager.getTransaction().begin();
                                s.endShiftFTW();
                                entityManager.getTransaction().commit();
                            }
                        }
                        else
                        {
                            String queryString = "SELECT p FROM ShiftPTW p WHERE p.worker = :login ORDER BY p.shiftStart DESC ";
                            Query query = entityManager.createQuery(queryString);
                            query.setParameter("login", logged);
                            List<ShiftPTW> products = query.getResultList();
                            if (products.size()==1){
                                System.out.println("Unable to close shift - non of yours is open");
                            }else {
                                //DATA UPDATE !
                                ShiftPTW s = entityManager.find(ShiftPTW.class, products.get(0).getID_PTWorker());
                                entityManager.getTransaction().begin();
                                s.endShiftPTW();
                                entityManager.getTransaction().commit();
                            }
                        }
                        System.out.println("You just closed your last shift !");

                        break;
                        //NOT YET UPDATED
//                    case "a":
//                        if (isAccountant==1) {
//                            AccountantPlatform test = new AccountantPlatform();
//                            test.menu(entityManager);
//                        }else {
//                            System.out.print("You have no power here :/");
//                        }
//                        break;
                    case "a":
                        if (isAccountant==1) {
                            System.out.println("Who would you like to add ? \n f - fulltime worker  \n p- part-time worker" );

                            try {
                                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                                element = obj.readLine();
                            } catch (IOException e) {
                                System.out.print("Unable to read character from file_type");
                            }

                            if (element.equals("p"))
                                addPartTimeWorker(entityManager);
                            else if (element.equals("f"))
                                addFullTimeWorker(entityManager);;
                        }else {
                            System.out.print("You have no power here :/");
                        }
                        break;
                    case "d":
                        if (isAccountant==1) {
                            System.out.println("Who would you like to delete ? \n f - fulltime worker  \n p- part-time worker" );
                            try {
                                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                                element = obj.readLine();
                            } catch (IOException e) {
                                System.out.print("Unable to read character from file_type");
                            }
                            if (element.equals("p"))
                                deletePartTimeWorker(entityManager);
                            else if (element.equals("f"))
                                deleteFullTimeWorker(entityManager , logged);
                        }else {
                            System.out.print("You have no power here :/");
                        }
                        break;
                    case "c":
                        sesja = "2";
                        break;
                }
            }
    }
    /**
     * Function with the menu, which allows user to log in, and to perform some operations
     * @param args
     * @throws SQLException
     */
    /**
     * Main class for whole project
     * @param args
     */
    public static void main(String[] args) {

        //data loading
        Scanner scan = new Scanner(System.in);
        loadData();
        String dbURL = "jdbc:derby://localhost:1527/BazaPracownikow;create=true";

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //logging in
        boolean logging = false;
        Worker logged = null;
        boolean is_fulltime = false;
        int is_accountant = 0;
        //DATA VALIDATION
        while (logging == false) {
            System.out.println("Login: \n");
            String Login = scan.nextLine();
            System.out.println("Password: \n");
            String Password = scan.nextLine();
            logged = loggin_in(Login, Password, entityManager);
            if (logged!=null)
            {
                logging = true;
            }
            else System.out.println("Unable to login");
            is_fulltime = isFulltime(Login, entityManager);
            if (is_fulltime == true)
                is_accountant = ifAccountantFt(Login, entityManager);
        }
        mainMenu(entityManager,logged,is_accountant,is_fulltime);
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
     * A method that permanently deletes Part Time Worker from  workers and Shifts
     * @param em entity manager for whole program
     */
    private static void deletePartTimeWorker(EntityManager em){
        //Show list of all part time worker
        String queryString = "SELECT p FROM PartTimeWorker p ";
        Query query = em.createQuery(queryString);
        List<PartTimeWorker> products = query.getResultList();
        for ( PartTimeWorker p : products) {
            System.out.println(p.toString());
        }
        System.out.println("please specify ID of worker \n ");
        String element ="";
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            element = obj.readLine();
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }
        PartTimeWorker w = em.find(PartTimeWorker.class ,Long.parseLong(element));
        //delete them from shifts
        if(w!= null) {
            String queryString2 = "DELETE FROM ShiftPTW p WHERE p.worker = :worker";
            Query query2 = em.createQuery(queryString2);
            query2.setParameter("worker", w);
            //delete this full time worker
            em.getTransaction().begin();
            em.remove(w);
            em.getTransaction().commit();
        }
        //delete this full time worker
    }

    /**
     * Methods that add part time worker
     * @param em
     */
    private static void addPartTimeWorker(EntityManager em){
        //Show list of all part time worker
       PartTimeWorker w = new PartTimeWorker();
        System.out.println("Add worker password \n ");
        String element = null ;
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            w.setPassword(obj.readLine());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }

        System.out.println("Add worker name \n ");
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            w.setName(obj.readLine());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }

        System.out.println("Add worker surname \n ");
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            w.setSurname(obj.readLine());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }
        w.createLogin();
        System.out.println("How much will he earn? \n ");
        int d =1 ;
        boolean t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                try {
                    d = Integer.parseInt(obj.readLine());
                    t = false;
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number !");
                }

            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        w.setCashPerHour(d);

        System.out.println("How much he needs to work per week? (between 0 and 40 in integer) \n ");
        element = "1";
         d =1 ;
         t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                try {
                    d = Integer.parseInt(obj.readLine());
                    t = false;
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number ! \n ");
                }

            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        System.out.println("who is his supervisor? \n ");
        w.setSupervisor(FullTimeWorker.chooseFullTimeWorker(em));

        System.out.println("Expiraion date of his Agreement \n ");
        int month =1 , year =2020 , day = 20;
        t=true;
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            while(t){
                System.out.println("year \n ");
                try {
                     year  = Integer.parseInt(obj.readLine());
                    t = false;
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number ! \n ");
                }
            }
            System.out.println("month \n ");
            t=true;
            while(t){
                System.out.println("month \n ");
                try {
                     month  = Integer.parseInt(obj.readLine());
                     if (month > 0 & month<12)
                        t = false;
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number ! \n ");
                }
            }
            System.out.println("day \n ");
            t=true;
            while(t){
                System.out.println("day \n ");
                try {
                    month  = Integer.parseInt(obj.readLine());
                    if (month > 0 & month<31)
                        t = false;
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number ! \n ");
                }
            }
            GregorianCalendar data = new GregorianCalendar(year, month, day);
            w.setExpirationDate(data.getTime());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }
        System.out.println("what is his department? \n ");
        w.setDepartment(Department.chooseDepartment(em));
        em.getTransaction().begin();
        em.persist(w);
        em.getTransaction().commit();
        //delete this full time worker
    }

    /**
     * Methods to add fulltime worker
     * @param em
     */
    private static void addFullTimeWorker(EntityManager em){
        //Show list of all part time worker
        FullTimeWorker w = new FullTimeWorker();
        System.out.println("Add worker password \n ");
        String element = null ;
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            w.setPassword(obj.readLine());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }

        System.out.println("Add worker name \n ");
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            w.setName(obj.readLine());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }

        System.out.println("Add worker surname \n ");
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            w.setSurname(obj.readLine());
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }
        w.createLogin();
        System.out.println("How much will he earn? \n ");
        int d =1 ;
        boolean t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                try {
                    d = Integer.parseInt(obj.readLine());
                    t = false;
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number !");
                }

            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        w.setCashPerHour(d);

        System.out.println("How much he gets of Paid leave ?? (between 0 and 40 in integer) \n ");
        d =1 ;
        t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                try {
                    d = Integer.parseInt(obj.readLine());
                    if (d >= 0 & d<= 40)
                    t = false;
                    else System.out.println("To much of paid leave !");
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number ! \n ");
                }

            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        w.setPaidLeave(d);
        System.out.println("How much he gets of Child leave ?? (between 0 and 20 in integer) \n ");
        d =1 ;
        t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                try {
                    d = Integer.parseInt(obj.readLine());
                    if (d >= 0 & d<= 20)
                        t = false;
                    else System.out.println("To much of child leave !");
                } catch (NumberFormatException nfe) {
                    System.out.print("It is not a number ! \n ");
                }

            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        System.out.print("Does he get any multisport ? \n y - yes n - no");
        w.setChildCareLeave(d);
        boolean b = true;
        t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                    element = (obj.readLine());
                    if (element.equals("y")){
                        b = true;
                    t = false;
                     }
                    else if (element.equals("n")) {
                        b = false;
                        t = false;
                    }
                        else System.out.println("Try once more !");


            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        w.setMultisport(b);
        System.out.println("Does he get any health care ? \n y - yes n - no");

        b = true;
        t = true;
        while (t) {
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                element = (obj.readLine());
                if (element.equals("y")){
                    b = true;
                    t = false;
                }
                else if (element.equals("n")) {
                    b = false;
                    t = false;
                }
                else System.out.println("Try once more !");


            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
        }
        w.setMultisport(b);
        System.out.println("what is his department? \n ");
        w.setDepartment(Department.chooseDepartment(em));
        em.getTransaction().begin();
        em.persist(w);
        em.getTransaction().commit();
        //delete this full time worker
    }
    /**
     * A method that permanently deletes Full Time Worker from  workers and Shifts
     * @author Aleksandra Rezetka
     * @param em entity manager for whole program
     */
    private static void deleteFullTimeWorker(EntityManager em , Worker logged){
        //Show list of all part time worker
        String queryString = "SELECT p FROM FullTimeWorker p ";
        Query query = em.createQuery(queryString);
        List<FullTimeWorker> products = query.getResultList();
        for ( FullTimeWorker p : products) {
            System.out.println(p.toString()+"\n please specify ID of worker ");
        }
        String element ="";
        try {
            BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
            element = obj.readLine();
        } catch (IOException e) {
            System.out.print("Unable to read character from file_type");
        }
        FullTimeWorker w = em.find(FullTimeWorker.class ,Long.parseLong(element));
        //delete them from shifts
        if(w!= null && !logged.equals(w)) {
            String queryString2 = "DELETE FROM ShiftFTW p WHERE p.worker = :worker";
            Query query2 = em.createQuery(queryString2);
            query2.setParameter("worker", w);
            String queryString3 = "SELECT p from PartTimeWorker p WHERE p.supervisor = :worker";
            Query query3 = em.createQuery(queryString3);
            query3.setParameter("worker", w);

            List<PartTimeWorker> products2 = query3.getResultList();

            for (PartTimeWorker f: products2) {
                em.getTransaction().begin();
                f.setSupervisor((FullTimeWorker) logged);
                em.getTransaction().commit();
            }
            //delete this full time worker
            em.getTransaction().begin();
            em.remove(w);
            em.getTransaction().commit();
        } else  if (logged.equals(w)){
            System.out.print("You can't delete yourself ! /n ");
        }
    }

    /**
     * @author Maciej Adamczyk
     * @param login
     * @param log_password
     * @param em
     * @return
     */
    private static Worker loggin_in(String login, String log_password, EntityManager em){
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        Query query = em.createQuery(queryString);
        query.setParameter("login", login);
        List<Worker> products = query.getResultList();
        if (products.size() > 0) {
            if(products.get(0).getPassword().equals(log_password)) {
                return products.get(0);
            }
            else
                return null;
        }
        else {
            String queryString2 = "SELECT p FROM PartTimeWorker p WHERE p.login LIKE :login";
            Query query2 = em.createQuery(queryString2);
            query2.setParameter("login", login);
            List<Worker> products2 = query2.getResultList();
            if (products2.size() > 0) {
                if(products2.get(0).getPassword().equals(log_password)) {
                    return products2.get(0);
                }
                else
                    return null;
            }
            else {
                return null;
            }

        }

    }

    /**
     * Checks if logged person is an accountant
     * @param login
     * @param em
     * @return
     */
    private static int ifAccountantFt(String login, EntityManager em) {
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        String queryString2 = "SELECT p FROM Department p";
        Query query = em.createQuery(queryString);
        query.setParameter("login", login);
        Query query2 = em.createQuery(queryString2);
        List<FullTimeWorker> products = query.getResultList();
        List<Department> products2 = query2.getResultList();
        if (products.get(0).getDepartment() == products2.get(6)) {
            return 1;
        }
        else return 0;
    }

    /**
     * Checks if logged person is an full time worker
     * @param login
     * @param em
     * @return
     */
    private static boolean isFulltime(String login, EntityManager em){
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        Query query = em.createQuery(queryString);
        query.setParameter("login", login);
        List<FullTimeWorker> products = query.getResultList();
        if (products.size() > 0) {
            if (products.get(0).getLogin().equals(login)) {
                return true;
            } else
                return false;
        }
        return false;
    }

}