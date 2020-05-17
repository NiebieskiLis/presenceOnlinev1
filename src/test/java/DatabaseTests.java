
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 *
 * @author AleksandraRezetka
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseTests {

    @Mock
    EntityManager em;
    EntityTransaction transaction = Mockito.mock(EntityTransaction.class);

    @Test
    public void howManyTimesIsCalled() {

    }

    @Test
    public void whenGivenLowercaseString_toUpperReturnsUppercase() {

        //Arrange
        String lower = "abcdef";

        //Act
        String result = lower.toUpperCase();

        //Assert
        assertEquals("ABCDEF", result);
    }



}
