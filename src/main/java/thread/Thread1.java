package thread;

import workers.Worker;
import workers.WorkersList;

import java.util.List;

/**
 *  @version 05/04/2020/v1
 *  @author Aleksandra Rezetka
 * a Thread that looks for all the people that earns less than a given number
 * that is implemented via Runnable (must contain run method)
 */
public class Thread1 implements Runnable {
    private List<Worker>  workers;
    private int cash;
    private WorkersList WBL;

    /**
     * An consturctor that takes Workers list to search in , payment below we search for and WorkerList
     * where we have synhronized add access to List of Workers that earns below our limit
     * @param w a list of workers which are going to be checked
     * @param c a limit of cash per hour below which we add to WL list
     * @param WL a list where we hold all the workers that earns below our limit
     */
    public Thread1( List<Worker>  w , int c , WorkersList WL) {
        this.workers = w;
        this.cash = c;
        this.WBL = WL;
    }

    /**
     * A method that implements how the thread works
     */
    public void  run(){
        for(int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getCashPerHour() < cash) {
                WBL.addToList(workers.get(i));
                System.out.print(workers.get(i).toString());
            }
        }
    }


}
