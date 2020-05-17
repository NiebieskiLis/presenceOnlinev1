
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
 *
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
        boolean logging = false;
        Worker logged = null;
        String login = "ARezetka";
        String password = "123";
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        List<FullTimeWorker> elemnents = new ArrayList<>();
        elemnents.add(test);
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        logged = ld.loggin_in(login, password , entityManager);
            if (logged!=null)
            {
                logging = true;
            }
            else System.out.println("Unable to login");
            assertEquals(true,logging);
        }
    public void mainMenuTestPartTimeLogging() {
        //logging in
        boolean logging = false;
        Worker logged = null;
        boolean is_fulltime = false;
        String login = "ARezetka";
        String password = "123";
        String queryString = "SELECT p FROM FullTimeWorker p WHERE p.login LIKE :login";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(null);
        String queryString2 = "SELECT p FROM PartTimeWorker p WHERE p.login LIKE :login";
        List<PartTimeWorker> elemnents = new ArrayList<>();
        elemnents.add( test2);
        Mockito.when(entityManager.createQuery(queryString2)).thenReturn(query2);
        Mockito.when(query2.getResultList()).thenReturn(elemnents);
        logged = ld.loggin_in(login, password , entityManager);
        if (logged!=null)
        {
            logging = true;
        }
        else System.out.println("Unable to login");
        assertEquals(true,logging);
    }
    @Test
    public void addFTW() {
        String queryString = "SELECT p FROM Department p ";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(1 , "ACCOUNTING"));
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        String element = "1";
        Mockito.when(entityManager.find(Department.class, Long.parseLong(element))).thenReturn(new Department(1 , "ACCOUNTING"));
        System.setIn(new ByteArrayInputStream("324\nAnna\nMariaj\n20\n3\n3\ny\nn\n1\n1\n".getBytes()));
        assertEquals(true,LoadingData.addFullTimeWorker(entityManager));
    }
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
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        String element = "1";
        Mockito.when(entityManager.find(FullTimeWorker.class, Long.parseLong("0"))).thenReturn(test);
        Mockito.when(entityManager.find(Department.class, Long.parseLong(element))).thenReturn(new Department(1 , "ACCOUNTING"));
        //User Actions
        System.setIn(new ByteArrayInputStream("324\nAnna\nMariaj\n20\n10\n0\n2020\n1\n12\n1\n0\n".getBytes()));
        //Act & Asset
        assertEquals(true,LoadingData.addPartTimeWorker(entityManager));
    }
    @Test
    public void ShiftTestFTW() {
        ShiftFTW s = new ShiftFTW(test);
        assertEquals(true , s.startShiftFTW());
        assertEquals(true , s.endShiftFTW());

    }
    @Test
    public void ShiftTestPTW() {
        ShiftPTW s = new ShiftPTW(test2);
        assertEquals(true , s.startShiftPTW());
        assertEquals(true , s.endShiftPTW());

    }
}
