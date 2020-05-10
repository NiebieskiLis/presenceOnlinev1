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
 * @update 10.05 to the database form
 */
@Entity
@Data
@Getter @Setter

public class ShiftPTW implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID_PTWorker;

    @ManyToOne
    private Worker worker;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private long noOfHours;
    public Worker getWorker() {
        return worker;
    }

    public ShiftPTW(Worker worker) {
        this.worker = (PartTimeWorker) worker;
        this.noOfHours = 0;
        this.shiftStart = null;
        this.shiftEnd = null;

    }

    /**
     * Method that starts shift
     */

    public void startShiftPTW(){
        setShiftStart(LocalDateTime.now());
        System.out.println("shift.Shift was started");
    }

    /**
     * Method that ends shift - it also calcuate noOfHours and takes in count night shifts
     */
    public void endShiftPTW(){
        setShiftEnd(LocalDateTime.now());
        long diff = java.time.Duration.between(getShiftStart(), shiftEnd).toHours();
        setNoOfHours(diff);
        System.out.println("shift.Shift was ended");
    }
}