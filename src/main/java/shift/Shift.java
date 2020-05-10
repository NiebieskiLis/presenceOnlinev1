package shift;

import workers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class represents worker shift
 * it has a data about the start and end time as well as sum of hours spend in work
 * * @version 28/03/2020/v1
 *  * @author Aleksandra Rezetka
 */
public class Shift implements Cloneable{
    private Worker worker;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private long noOfHours;

    /**
     * When we create a shift we only know to which worker it is assigned
     * @param worker
     */

    public Shift(Worker worker) {
        this.worker = worker;
        this.noOfHours = 0;
        this.shiftStart = null;
        this.shiftEnd = null;

    }


    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public LocalDateTime getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(LocalDateTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalDateTime getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(LocalDateTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public long getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(long noOfHours) {
        this.noOfHours = noOfHours;
    }

    /**
     * Method that starts shift
     */

    public void startShift(){
        setShiftStart(LocalDateTime.now());
        System.out.println("shift.Shift was started");
    }

    /**
     * Method that ends shift - it also calcuate noOfHours and takes in count night shifts
     */
    public void endShift(){
        setShiftEnd(LocalDateTime.now());
        long diff = java.time.Duration.between(getShiftStart(), shiftEnd).toHours();
        setNoOfHours(diff);
        System.out.println("shift.Shift was ended");
    }

    /**
     * This information give all data about shift
     */
    public void giveInormationAboutShift(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        if(getShiftStart()!= null) {
            System.out.println("shift.Shift started at " + dtf.format(getShiftStart()));
            if (getShiftEnd() != null) {
                System.out.println("shift.Shift ended at " + dtf.format(getShiftEnd()));
            } else System.out.println("shift.Shift didn't end yet");
        }else System.out.println("shift.Shift didn't start yet");

    }

    /**
     *This is my implementation of the deep clone for shifts
     * @return a cloned object
     * @throws CloneNotSupportedException exception is thrown when we are unable to clone
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Shift cloned = (Shift) super.clone();
        cloned.setWorker((Worker)cloned.getWorker().clone());
        return cloned;
    }

    /**
     * This Method Main is used for tests of shift.Shift it shows the usage of shift and its functions
     * @param args
     */
    public static void main(String[] args) {
        FullTimeWorker p1 = new FullTimeWorker("Passw0rd", "Mikolaj", "Trapek", 34, 5, false, true, 4);
        System.out.println(p1.getName());
        //usage of enum type
        PartTimeWorker p2 = new PartTimeWorker("Passw0rd", "Franek", "Tapla", 21, 6, PartTimeWorker.Department.IT, p1, 2020, 12, 15);
        System.out.println(p2.getName());

        List<Worker> workers = new ArrayList<Worker>();
        //Here I showed how polymorphism works
        workers.add(p1);
        workers.add(p2);
        //Uzycie pętli (punkt 0)
        workers.forEach(p -> p.introduceWorker());

        //shift.Shift Test
        Shift z1 = new Shift(p1);
    }


}
