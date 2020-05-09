package database;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;
import java.util.GregorianCalendar;

@Data
@Entity
public class PartTimeWorker  {
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
    @ManyToOne
    private FullTimeWorker supervisor;
    @Getter @Setter
    private Date expirationDate;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Department department;

    public  PartTimeWorker(){

    }
    public PartTimeWorker(String login, String password, String name, String surname, int cashPerHour,  String login1, String password1, String name1, String surname1, int cashPerHour1, int minNumberOfHours, FullTimeWorker supervisor, Date expirationDate, Department department) {
        this.ID_PartTimeWorker = ID_PartTimeWorker;
        this.login = login1;
        this.password = password1;
        this.name = name1;
        this.surname = surname1;
        this.cashPerHour = cashPerHour1;
        this.minNumberOfHours = minNumberOfHours;
        this.supervisor = supervisor;
        this.expirationDate = expirationDate;
        this.department = department;
    }

    public PartTimeWorker(String password, String name, String surname, int cashPerHour, int minNumberOfHours, Department department, FullTimeWorker supervisor, int year, int month, int day) {
        String login1 = name.charAt(0) + surname;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cashPerHour = cashPerHour;
        this.minNumberOfHours = minNumberOfHours;
        this.department = department;
        this.supervisor = supervisor;
        GregorianCalendar data = new GregorianCalendar(year, month, day);
        this.expirationDate = data.getTime();
    }
    public PartTimeWorker(String login, String password, String name, String surname, int cashPerHour, int minNumberOfHours, Department department, FullTimeWorker supervisor, int year, int month, int day) {
//        String login1 = name.charAt(0) + surname;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cashPerHour = cashPerHour;
        this.minNumberOfHours = minNumberOfHours;
        this.department = department;
        this.supervisor = supervisor;
        GregorianCalendar data = new GregorianCalendar(year, month, day);
        this.expirationDate = data.getTime();
    }
    public PartTimeWorker(int ID_PartTimeWorker, String login, String password, String name, String surname, int cashPerHour, int minNumberOfHours, FullTimeWorker supervisor,  int year, int month, int day, Department department) {
        this.ID_PartTimeWorker = ID_PartTimeWorker;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cashPerHour = cashPerHour;
        this.minNumberOfHours = minNumberOfHours;
        this.supervisor = supervisor;
        GregorianCalendar data = new GregorianCalendar(year, month, day);
        this.expirationDate = data.getTime();
        this.department = department;
    }
}
