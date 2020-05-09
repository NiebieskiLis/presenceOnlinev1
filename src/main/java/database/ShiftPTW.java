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
public class ShiftPTW implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID_PTWorker;
    @Getter
    @Setter
    @ManyToOne
    private PartTimeWorker worker;
    @Getter @Setter
    private LocalDateTime shiftStart;
    @Getter @Setter
    private LocalDateTime shiftEnd;
    @Getter @Setter
    private long noOfHours;
    public PartTimeWorker getWorker() {
        return worker;
    }

    public void setWorker(PartTimeWorker worker) {
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
}