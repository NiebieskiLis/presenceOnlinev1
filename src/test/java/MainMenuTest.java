import javax.persistence.*;
import static org.junit.Assert.*;
import database.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


/**
 *A class that tests all functionalities of Main Menu of app
 * @author AleksandraRezetka
 */
@RunWith(MockitoJUnitRunner.class)
public class MainMenuTest {

    EntityManager entityManager= Mockito.mock(EntityManager.class);
    EntityTransaction transaction = Mockito.mock(EntityTransaction.class);
    LoadingData ld = new LoadingData();
    Query query = Mockito.mock(Query.class);
    Query query2 = Mockito.mock(Query.class);
    FullTimeWorker test = new FullTimeWorker("123","Aleksandra","Rezetka",23,2,false,false,45,new Department("ACCOUNTING"));
    PartTimeWorker test2 = new PartTimeWorker("123",  "Aleksandra", "Rezetka", 10, 11,  new Department("ACCOUNTING"),test,2020, 11, 20);
    /**
     * A set of test that checks main Menu behaviour
     */
    @Test
    public void mainMenuTestLogging() {
        //logging in
        //Arrange
        boolean logging = false;
        Worker logged = null;
        String login = "ARezetka";
        String password = "123";
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        List<FullTimeWorker> elemnents = new ArrayList<>();
        elemnents.add(test);
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        //Act
        logged = ld.loggin_in(login, password , entityManager);
            if (logged!=null)
            {
                logging = true;
            }
            else System.out.println("Unable to login");
            //Assert
            assertTrue("Logging Test was Passed",logging);
        }

    /**
     * A method that test main menu options
     */
    @Test
    public void mainMenuTestPartTimeLogging() {
        //logging in
        //Arrange
        boolean logging = false;
        Worker logged = null;
        String login = "ARezetka";
        List<FullTimeWorker> f = new ArrayList<>();

        String password = "123";
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(f);
        String queryString2 = "SELECT p FROM PartTimeWorker p WHERE p.login LIKE :login";
        List<PartTimeWorker> elemnents = new ArrayList<>();
        elemnents.add( test2);
        Mockito.when(entityManager.createQuery(queryString2)).thenReturn(query2);
        Mockito.when(query2.getResultList()).thenReturn(elemnents);
        //Act
        logged = ld.loggin_in(login, password , entityManager);
        if (logged!=null)
        {
            logging = true;
        }
        else System.out.println("Unable to login");
        //Asset
        assertTrue("Login Test Passed !",logging);
    }
    /**
     * A logging when a worker is not in the database
     */
    @Test
    public void notInDatabase() {
        //logging in
        //Arrange
        boolean logging = false;
        Worker logged = null;
        String login = "X";
        List<FullTimeWorker> f = new ArrayList<>();
        List<PartTimeWorker> p = new ArrayList<>();

        String password = "123";
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(f);
        String queryString2 = "SELECT p FROM PartTimeWorker p WHERE p.login LIKE :login";
        Mockito.when(entityManager.createQuery(queryString2)).thenReturn(query2);
        Mockito.when(query2.getResultList()).thenReturn(p);
        //Act
        logged = ld.loggin_in(login, password , entityManager);
        if (logged!=null)
        {
            logging = true;
        }
        else System.out.println("Unable to login");
        //Asset
        assertTrue("Login Test Passed !",!logging);
    }

    /**
     * A method that tests how adding fullTimeWorker works
     */
    @Test
    public void addFTW() {
        //Arrange
        String queryString = "SELECT p FROM Department p ";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(1 , "ACCOUNTING"));
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        String element = "1";
        Mockito.when(entityManager.find(Department.class, Long.parseLong(element))).thenReturn(new Department(1 , "ACCOUNTING"));
        System.setIn(new ByteArrayInputStream("324\nAnna\nMariaj\n20\n3\n3\ny\nn\n1\n1\n".getBytes()));
        //Act&Asset
        assertTrue("Adding FullTimeWorker Test Passed",LoadingData.addFullTimeWorker(entityManager));
    }

    /**
     * A method that tests how adding fullTimeWorker works when one of the inputs is unproper
     */
    @Test
    public void addFTWUnrpoperInput() {
        //Arrange
        String queryString = "SELECT p FROM Department p ";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(1 , "ACCOUNTING"));
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        String element = "1";
        Mockito.when(entityManager.find(Department.class, Long.parseLong(element))).thenReturn(new Department(1 , "ACCOUNTING"));
        //Here User put inncorect values
        //INPROPER USER ACTION - INSTEAD ON N OR T HE PUTS NUMBER 1

        System.setIn(new ByteArrayInputStream("324\nAnna\nMariaj\n20\n3\n3\ny\n1\nn\n1\n1\n".getBytes()));
        //Act&Asset
        assertTrue("Adding FullTimeWorker Test Passed",LoadingData.addFullTimeWorker(entityManager));
    }
    /**
     * A method that tests how adding PartTimeWorker works
     */
    @Test
    public void addPTW() {
        //Arrange Department Class
        String queryString = "SELECT p FROM Department p ";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(1 , "ACCOUNTING"));
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        //Arrange FTW
        String queryString2 = "SELECT p FROM FullTimeWorker p ";
        Mockito.when(entityManager.createQuery(queryString2)).thenReturn(query2);
        List<FullTimeWorker> elemnentsf = new ArrayList<>();
        elemnentsf.add(test);
        Mockito.when(query2.getResultList()).thenReturn(elemnentsf);
        String element = "1";
        Mockito.when(entityManager.find(FullTimeWorker.class, Long.parseLong("0"))).thenReturn(test);
        Mockito.when(entityManager.find(Department.class, Long.parseLong(element))).thenReturn(new Department(1 , "ACCOUNTING"));
        //User Actions
        System.setIn(new ByteArrayInputStream("324\nAnna\nMariaj\n20\n10\n0\n2020\n1\n12\n1\n0\n".getBytes()));
        //Act & Asset
        assertTrue("Adding PartTimeWorker Test Passed",LoadingData.addPartTimeWorker(entityManager));
    }

    /**
     * A method that tests shifts of fullTimeWorker
     */
    @Test
    public void ShiftTestFTW() {
        //Arrange
        ShiftFTW s = new ShiftFTW(test);
        //Act & Asset

        assertTrue("Shift Start Test Passed" , s.startShiftFTW());
        assertTrue("Shift End Test Passed" , s.endShiftFTW());
    }
    /**
     * A method that tests shifts of partTimeWorker
     */
    @Test
    public void ShiftTestPTW() {
        //Arrange
        ShiftPTW s = new ShiftPTW(test2);
        //Act & Asset
        assertTrue("Shift Start Test Passed" , s.startShiftPTW());
        assertTrue("Shift End Test Passed" , s.endShiftPTW());

    }
}
