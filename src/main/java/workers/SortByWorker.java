package workers;

import workers.Worker;
import java.util.Comparator;

/**
 * This class implements interface Comparator for the worker class
 */

public class SortByWorker implements Comparator<Worker>
{
    /** compares two workers by surname and name
     * @param a gets worker A
     * @param b gets worker b
     * @return it returns 0 if equals 1 if a is bigger and -1 if lower
     */
    public int compare(Worker a, Worker b)
    {
        return a.compareTo(b);
    }
}