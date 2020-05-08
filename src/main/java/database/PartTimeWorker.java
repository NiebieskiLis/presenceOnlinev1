package database;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import workers.Worker;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
@Entity
public class PartTimeWorker extends Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID_PartTimeWorker;
    @Getter @Setter
    private String login;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String surname;
    @Getter @Setter
    private int cashPerHour;
    @Getter @Setter
    private int minNumberOfHours;
    @Getter @Setter
    private workers.PartTimeWorker.Department department;
    @Getter @Setter
    private Worker supervisor;
    @Getter @Setter
    private Date expirationDate;

}
