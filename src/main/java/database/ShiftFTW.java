package database;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

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
    private long ID_Worker;
    @ManyToOne
    private FullTimeWorker worker;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private long noOfHours;

}