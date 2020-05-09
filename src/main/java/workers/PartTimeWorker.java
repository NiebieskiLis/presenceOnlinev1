package workers;

import java.util.Date;
import java.util.GregorianCalendar;



/** I assumed that a part time worker doesn't get any benefits form company and has an supervisor assigned
 * Company also need to connect him to specific department in accordence to the list with enums
 * Company also believes that a deal needs to have a specific. If not you need to specify date like 2100.01.01
 *  @version 28/03/2020/v1
 *  @author Aleksandra Rezetka
 */

public class PartTimeWorker extends Worker {
    private int minNumberOfHours;
    private Department department;
    private Worker supervisor;
    private Date expirationDate;
    //usage of enum type
    public enum Department {
        IT, HR, FINANCE, HQ,
        DEVELOPMENT, ANALYSIS
    }
    /**
     * constructor for an abstract class that gets all the info about worker
     * @param login Workers.PartTimeWorker's login
     * @param password    Workers.PartTimeWorker's password
     * @param name        Workers.PartTimeWorker name
     * @param surname     His surname
     * @param cashPerHour amount paid per hour
     * @param minNumberOfHours  minimum number employee must work per week
     * @param department   a department to which employee is assigned
     * @param supervisor a Workers.PartTimeWorker supervisor
     * @param year a year when employment deal was signed
     * @param month  a month when employment deal was signed
     * @param day    a day when employment deal was signed
     */
    public PartTimeWorker(String login, String password, String name, String surname, int cashPerHour, int minNumberOfHours, Department department, Worker supervisor, int year, int month, int day) {
        super(login, password, name, surname, cashPerHour);
        this.minNumberOfHours = minNumberOfHours;
        this.department = department;
        this.supervisor = supervisor;
        GregorianCalendar data = new GregorianCalendar(year, month, day);
        this.expirationDate = data.getTime();
    }

    /**
     * constructor for an abstract class that gets all the info about worker except login which is created automatically
     * @param password    Workers.PartTimeWorker's password
     * @param name        Workers.PartTimeWorker name
     * @param surname     His surname
     * @param cashPerHour amount paid per hour
     * @param minNumberOfHours  minimum number employee must work per week
     * @param department   a department to which employee is assigned
     * @param supervisor a Workers.PartTimeWorker supervisor
     * @param year a year when employment deal was signed
     * @param month  a month when employment deal was signed
     * @param day    a day when employment deal was signed
     */
    public PartTimeWorker(String password, String name, String surname, int cashPerHour, int minNumberOfHours, Department department, Worker supervisor, int year, int month, int day) {
        super(password, name, surname, cashPerHour);
        this.minNumberOfHours = minNumberOfHours;
        this.department = department;
        this.supervisor = supervisor;
        GregorianCalendar data = new GregorianCalendar(year, month, day);
        this.expirationDate = data.getTime();
    }
    public Worker getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Worker supervisor) {
        this.supervisor = supervisor;
    }

    public int getMinNumberOfHours() {
        return minNumberOfHours;
    }

    public void setMinNumberOfHours(int MinNumberOfHours) {
        this.minNumberOfHours = MinNumberOfHours;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    /**
     * This method enables required person to check all the data about part-time worker employment. It prints data about supervisor ane expiration date of employment deal
     */
    public void giveInformationAboutEmployment(){
        System.out.println("Expiration date of his >employment deal : " +getExpirationDate().toString() );
        System.out.println("Working in department : " + getDepartment() + " supervisor " + getSupervisor().getSurname() + " " + getSupervisor().getName()  );
    }
    /**
     * This method is an extension to Workers.Worker introduction implemented in Workers.Worker class
     */
    @Override
    public void introduceWorker() {
        super.introduceWorker();
        System.out.println("He is in department " + getDepartment());
        System.out.println("His superviser is : " + getSupervisor().getName() + " " + getSupervisor().getSurname());
    }
}
