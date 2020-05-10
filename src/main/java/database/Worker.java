package database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Scanner;

/**
 * This is an abstract class that is a base for all of the kinds of workers
 * @version 28/03/2020/v1
 * @author Aleksandra Rezetka
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @Setter
public abstract class Worker implements Comparable<Worker> , Cloneable , Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected long ID;
    protected String login;
    protected String password;
    protected String name;
    protected String surname;
    protected int cashPerHour;



    /**
     * constructor for an abstract class that gets all the info about worker
     *
     * @param login       Workers.Worker's login
     * @param password    Workers.Worker's password
     * @param name        -Workers name
     * @param surname     His surname
     * @param cashPerHour amount paid per hour
     */
    public Worker(String login, String password, String name, String surname, int cashPerHour) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cashPerHour = cashPerHour;
    }

    /**
     * constructor for an abstract class that gets all the info about worker except his login - it creates it itself
     *
     * @param password    Workers.Worker's password
     * @param name        -Workers name
     * @param surname     His surname
     * @param cashPerHour amount paid per hour
     */
    public Worker(String password, String name, String surname, int cashPerHour) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.cashPerHour = cashPerHour;
        String login1 = name.charAt(0) + surname;
        this.login = login1.toLowerCase();
    }
    public Worker(){

    }
    /**
     * Method that is used to describe Employee
     */
    public void introduceWorker() {

        System.out.println("Employer Data :");
        try {
            System.out.println(getName() + " " + getSurname());
        } catch (Exception e) {
            System.out.println("Employer Data are LOST !");
        }
    }

    /**
     * mathod that allows Employee to change his password
     *
     * @param oldPassword
     */
    public void changePassword(String oldPassword) {

        if (oldPassword.equals(getPassword())) {
            Scanner input = new Scanner(System.in);
            System.out.print("Type a new password: ");
            String password = input.next();
            setPassword(password);
        } else System.out.print("Given password is invalid :<");
    }

    /**Here is implementation of natural ordering of Workers by their name and surname
     * @param wk is a worker we try to compare our to
     * @return value 0 is returned when workers are equal
     */
    public int compareTo(Worker wk){
        if(surname.equals(wk.getSurname()))
        {
            if(name.equals(wk.getName()))
                return 0;
            else if(name.compareTo(wk.name)>0)
                return 1;
            else
                return -1;
        }
        else if(surname.compareTo(wk.surname)>0)
            return 1;
        else
            return -1;
    }

    /**
     * Override of a toString() method that enables printing name and a surname of a worker
     * @return
     */
    @Override
    public String toString() {
        return getLogin()+ " "+getID() +" "+getName() +" "+ getSurname();
    }

    /**
     *Method that enables clone of Workers.Worker
     * @return cloned object
     * @throws CloneNotSupportedException which apperas when
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
