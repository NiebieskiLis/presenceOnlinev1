package database;

import jdk.jfr.Enabled;
import workers.Worker;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A full time worker is a worker that is able to get any benefits from the company as well as to get paid leave
 * @version 28/03/2020/v1
 *  @author Aleksandra Rezetka
 */
@Entity
public class FullTimeWorker extends Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID_FullTimeWorker;
    private int paidLeave;
    private boolean multisport;
    private boolean healthcareLeave;
    private int childCareLeave;

    @Override
    public void introduceWorker ()
    {
        super.introduceWorker();
        System.out.println ("He is a full-time Workers.Worker");
    }
}