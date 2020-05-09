package database;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class LoadingData {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");

    public static void main(String[] args){
        System.out.print("Hello World\n");
        loadData();
        entityManagerFactory.close();
        System.out.print("Bye World");
    }

    private static void loadData()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Test testShift = new Test();
        testShift.setPole1(10);
        entityManager.persist(testShift);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}