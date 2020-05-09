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
    @ManyToOne
    private Department department;

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