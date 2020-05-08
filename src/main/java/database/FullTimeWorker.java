package database;

import workers.Worker;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.Data;

/**
 * A full time worker is a worker that is able to get any benefits from the company as well as to get paid leave
 * @version 28/03/2020/v1
 *  @author Aleksandra Rezetka
 */
@Entity
@Data
public class FullTimeWorker extends Worker {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID_FullTimeWorker;
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
    private int paidLeave;
    @Getter @Setter
    private boolean multisport;
    @Getter @Setter
    private boolean healthcareLeave;
    @Getter @Setter
    private int childCareLeave;
}