package workers;

import java.util.Comparator;

/**
 * this is a comparator that allows sorting workers by their wage from the highest to the lowest
 *  @version 05/04/2020/v1
 *  @author Aleksandra Rezetka
 */
public class SortByCashPerHour implements Comparator<Worker>
{
    /**
     * Compares two workers by the cash they earn
     * @param a gets worker A
     * @param b gets worker b
     * @return it returns difference between earnings of two employees
     */
    public int compare(Worker a, Worker b)
    {
        return b.getCashPerHour()- a.getCashPerHour() ;
    }
}

