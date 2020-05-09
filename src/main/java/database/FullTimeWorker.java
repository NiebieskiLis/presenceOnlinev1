package database;

import workers.Worker;

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
    @ManyToOne(cascade = {CascadeType.ALL})
    private Department department;

    public FullTimeWorker(long ID_FullTimeWorker, String login, String password, String name, String surname, int cashPerHour, int paidLeave, boolean multisport, boolean healthcareLeave, int childCareLeave, Department department) {
        this.ID_FullTimeWorker = ID_FullTimeWorker;
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
    public FullTimeWorker(){

    }
    public long getID_FullTimeWorker() {
        return ID_FullTimeWorker;
    }

    public void setID_FullTimeWorker(long ID_FullTimeWorker) {
        this.ID_FullTimeWorker = ID_FullTimeWorker;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int getCashPerHour() {
        return cashPerHour;
    }

    @Override
    public void setCashPerHour(int cashPerHour) {
        this.cashPerHour = cashPerHour;
    }

    public int getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(int paidLeave) {
        this.paidLeave = paidLeave;
    }

    public boolean isMultisport() {
        return multisport;
    }

    public void setMultisport(boolean multisport) {
        this.multisport = multisport;
    }

    public boolean isHealthcareLeave() {
        return healthcareLeave;
    }

    public void setHealthcareLeave(boolean healthcareLeave) {
        this.healthcareLeave = healthcareLeave;
    }

    public int getChildCareLeave() {
        return childCareLeave;
    }

    public void setChildCareLeave(int childCareLeave) {
        this.childCareLeave = childCareLeave;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}