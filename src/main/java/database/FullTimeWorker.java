package database;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.Data;

/**
 * A full time worker is a worker that is able to get any benefits from the company as well as to get paid leave
 * @version 09/05/2020/v1
 *  @author Aleksandra Rezetka
 */
@Entity
@Data
@Getter @Setter
public class FullTimeWorker extends Worker {


    private int paidLeave;
    private boolean multisport;
    private boolean healthcareLeave;
    private int childCareLeave;
    @ManyToOne(cascade = {CascadeType.ALL})
    private Department department;

    public FullTimeWorker(long ID_FullTimeWorker, String login, String password, String name, String surname, int cashPerHour, int paidLeave, boolean multisport, boolean healthcareLeave, int childCareLeave, Department department) {
        this.ID = ID_FullTimeWorker;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cashPerHour = cashPerHour;
        this.paidLeave = paidLeave;
        this.multisport = multisport;
        this.healthcareLeave = healthcareLeave;
        this.childCareLeave = childCareLeave;
        this.department = department;
    }

    public FullTimeWorker(String login1, String password1, String name1, String surname1, int cashPerHour1, int paidLeave, boolean multisport, boolean healthcareLeave, int childCareLeave, Department department) {
        this.login = login1;
        this.password = password1;
        this.name = name1;
        this.surname = surname1;
        this.cashPerHour = cashPerHour1;
        this.paidLeave = paidLeave;
        this.multisport = multisport;
        this.healthcareLeave = healthcareLeave;
        this.childCareLeave = childCareLeave;
        this.department = department;
    }

    public FullTimeWorker(String password1, String name1, String surname1, int cashPerHour1, int paidLeave, boolean multisport, boolean healthcareLeave, int childCareLeave, Department department) {
        this.login = name1.charAt(0) + surname1;
        this.password = password1;
        this.name = name1;
        this.surname = surname1;
        this.cashPerHour = cashPerHour1;
        this.paidLeave = paidLeave;
        this.multisport = multisport;
        this.healthcareLeave = healthcareLeave;
        this.childCareLeave = childCareLeave;
        this.department = department;
    }

    public FullTimeWorker() {

    }
}