package database;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



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
}
