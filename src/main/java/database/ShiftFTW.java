package database;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents worker shift
 * it has a data about the start and end time as well as sum of hours spend in work
 * @version 08/05/2020/v2
 * @author Aleksandra Rezetka
 */
@Entity
@Data
public class ShiftFTW implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID_FTWorker;
    @Getter
    @Setter
    @ManyToOne
    private Worker worker;
    @Getter @Setter
    private LocalDateTime shiftStart;
    @Getter @Setter
    private LocalDateTime shiftEnd;
    @Getter @Setter
    private long noOfHours;
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

    public  ShiftFTW(){

    }
    /**
     * Method that starts shift
     */

    public ShiftFTW(Worker worker) {
        this.worker = (FullTimeWorker) worker;
        this.noOfHours = 0;
        this.shiftStart = null;
        this.shiftEnd = null;

    }
    public boolean startShiftFTW(){
        setShiftStart(LocalDateTime.now());
        System.out.println("shift.Shift was started");
        return true;
    }

    /**
     * Method that ends shift - it also calcuate noOfHours and takes in count night shifts
     */
    public boolean endShiftFTW(){
        setShiftEnd(LocalDateTime.now());
        long diff = java.time.Duration.between(getShiftStart(), shiftEnd).toHours();
        setNoOfHours(diff);
        System.out.println("shift.Shift was ended");
        return true;
    }
}