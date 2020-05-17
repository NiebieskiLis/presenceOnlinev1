
import javax.persistence.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import com.jayway.jsonpath.InvalidPathException;
import database.CreateData;
import database.Department;
import database.FullTimeWorker;
import database.PartTimeWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;

/**
 *
 * @author AleksandraRezetka
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseTests {

    EntityManager entityManager= Mockito.mock(EntityManager.class);
    EntityTransaction transaction = Mockito.mock(EntityTransaction.class);
    /**
     * A test that checks data load function
     */
    @Test
    public void addDataToDatabase() {
        CreateData ld = new CreateData();
        Mockito.lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        assertEquals(true ,ld.createDepartments("Data/Department.json",entityManager));
    }
    @Test
    public void addDataToDatabaseWithInvalidPath() {
        CreateData ld = new CreateData();
        Mockito.lenient().when(entityManager.getTransaction()).thenReturn(transaction);
        assertEquals(false ,ld.createDepartments("Data/Departments.json",entityManager));

    }
}
