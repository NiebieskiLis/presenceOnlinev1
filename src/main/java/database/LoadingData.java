package database;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
public class LoadingData {
    private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DB");

    public static void main(String[] args){
        System.out.print("Hello World\n");
        loadData();
        System.out.print("Bye World");
    }

    private static void loadData()
    {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
      //  entityManager.getTransaction().begin();
        CreateData cr = new CreateData();
        cr.createDepartments("Data/Department.json",entityManager);
        System.out.println("Poza departmentem");
        cr.createFullTimeWorker("Data/workersFT.json",entityManager);
        System.out.println("Poza FTW");

        cr.createPartTimeWorker("Data/workersPT.json",entityManager);
        entityManager.close();
    }
}