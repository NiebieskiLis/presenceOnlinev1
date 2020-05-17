
import javax.persistence.*;
import static org.junit.Assert.*;
import database.CreateData;
import database.Department;
import database.FullTimeWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AleksandraRezetka
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseTests {

    EntityManager entityManager= Mockito.mock(EntityManager.class);
    EntityTransaction transaction = Mockito.mock(EntityTransaction.class);
    Query query = Mockito.mock(Query.class);
    Query query2 = Mockito.mock(Query.class);

    FullTimeWorker test = new FullTimeWorker("123","Aleksandra","Rezetka",23,2,false,false,45,new Department("ACCOUNTING"));

    /**
     * A test that checks data load function for Departments
     */
    @Test
    public void addDataToDatabase() {
        CreateData ld = new CreateData();
        Mockito.lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        assertEquals(true ,ld.createDepartments("Data/Department.json",entityManager));
    }
    /**
     * A method that adds Departments from invalid path
     */
    @Test
    public void addDataToDatabaseWithInvalidPath() {
        CreateData ld = new CreateData();
        Mockito.lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        assertEquals(false ,ld.createDepartments("Data/Departments.json",entityManager));

    }

    /**
     * A method that tries to add fullTimeWorker from path
     */
    @Test
    public void addDataToDatabaseFullTimeWorker() {
        CreateData ld = new CreateData();
        String queryString = "SELECT p FROM Department p";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(0 , "ACCOUNTING"));
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        assertEquals(true ,ld.createFullTimeWorker("Data/workersFT.json",entityManager));
    }
    /**
     * A method that adds FullTimeWorker from invalid path
     */
    @Test
    public void addDataToDatabaseFullTimeWorkerInvalidPath() {
        CreateData ld = new CreateData();
        String queryString = "SELECT p FROM Department p";
        Mockito.lenient().when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(0 , "ACCOUNTING"));
        Mockito.lenient().when(query.getResultList()).thenReturn(elemnents);
        Mockito.lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        assertEquals(false ,ld.createFullTimeWorker("Data/workersfsFT.json",entityManager));
    }

    /**
     * A method that tries to add partTimeWorker from path
     */
    @Test
    public void addDataToDatabasePartTimeWorker() {
        CreateData ld = new CreateData();
        String queryString = "SELECT p FROM Department p";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(0 , "ACCOUNTING"));
        Mockito.when(query.getResultList()).thenReturn(elemnents);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        String queryString2 = "SELECT p FROM FullTimeWorker p";
        Mockito.when(entityManager.createQuery(queryString2)).thenReturn(query2);
        List<FullTimeWorker> elemnentsF = new ArrayList<>();
        elemnentsF.add(test);
        Mockito.when(query2.getResultList()).thenReturn(elemnentsF);
        assertEquals(true ,ld.createPartTimeWorker("Data/workersPT.json",entityManager));
    }

    /**
     * A method that adds PartTimeWorker from invalid path
     */
    @Test
    public void addDataToDatabasePartTimeWorkerInvalidPath() {
        CreateData ld = new CreateData();
        String queryString = "SELECT p FROM Department p";
        Mockito.when(entityManager.createQuery(queryString)).thenReturn(query);
        List<Department> elemnents = new ArrayList<>();
        elemnents.add(new Department(0 , "ACCOUNTING"));
        Mockito.lenient().when(query.getResultList()).thenReturn(elemnents);
        Mockito.lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        String queryString2 = "SELECT p FROM FullTimeWorker p";
        Mockito.lenient().when(entityManager.createQuery(queryString2)).thenReturn(query2);
        List<FullTimeWorker> elemnentsF = new ArrayList<>();
        elemnentsF.add(test);
        Mockito.lenient().when(query2.getResultList()).thenReturn(elemnentsF);
        assertEquals(false ,ld.createPartTimeWorker("Data/woafetrkersPT.json",entityManager));
    }
}
