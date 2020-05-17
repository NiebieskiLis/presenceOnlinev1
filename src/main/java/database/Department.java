package database;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * This class represents Departments in the company
 * it has a data about the start and end time as well as sum of hours spend in work
 * @version 08/05/2020/v2
 * @author Aleksandra Rezetka
 */
@Entity
@Data
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID_Department;
    private String Department;

    public Department() {
    }

    public Department(long ID_Department, String department) {
        Department = department;
    }
    public Department( String department) {
        Department = department;
    }
    public static Department chooseDepartment(EntityManager em , BufferedReader obj){
        String element = "";
        Department x =null;
        while (x == null) {
            System.out.println("Which Department? \n ");
            String queryString = "SELECT p FROM Department p ";
            Query query = em.createQuery(queryString);
            List<Department> products = query.getResultList();
            for (Department p : products) {
                System.out.println(p.toString());
            }
            System.out.println("Please specify ID \n ");
            try {
                element = obj.readLine();
            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
            try {
                x = em.find(Department.class, Long.parseLong(element));
            }catch (NumberFormatException nfe) {
                System.out.print("It is not a number !");
                x=null;
            }
        }
        return x;
    }
}
