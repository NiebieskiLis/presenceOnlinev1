package database;
import javax.persistence.*;
import lombok.Data;

import java.util.Set;


/**
 * This class represents worker shift
 * it has a data about the start and end time as well as sum of hours spend in work
 * @version 08/05/2020/v2
 * @author Aleksandra Rezetka
 */
@Entity
@Data
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
    public long getID_Department() {
        return ID_Department;
    }

    public void setID_Department(long ID_Department) {
        this.ID_Department = ID_Department;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }
}
