package test;
import shift.Shift;
import workers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @version 05/04/2020/v1
 * @author Aleksandra Rezetka
 * This class has a purpose of presenting deep cloning and sorting with comperable and comperator that I've implemented
 */
public class TestClone
{
    public static void main(String[] args) throws CloneNotSupportedException
    {
        FullTimeWorker p1 = new FullTimeWorker("Passw0rd", "Mikolaj", "Trapek", 34, 5, false, true,4);
        //usage of enum type
        PartTimeWorker p2 = new PartTimeWorker("Passw0rd", "Franek", "Tapla", 21, 6, PartTimeWorker.Department.IT, p1, 2020 , 12 , 15);

        List<Worker> workers=new ArrayList<Worker>();
        //Polymorphism and how it works
        workers.add(p1);
        workers.add(p2);

        //Deep cloning representation
        Shift z1 = new Shift(p1);
        Shift cloned = (Shift) z1.clone();
        cloned.getWorker().setName("Jakub");

        System.out.println(z1.getWorker().getName());
        System.out.println(cloned.getWorker().getName());


        String fileName = "Data\\FT1.json";

        //Creates workers with creator

        CreateWorker cr = new CreateWorker();
        workers.addAll( cr.createFullTimeWorker(fileName));
        SortByCashPerHour workerComparator = new SortByCashPerHour();
        //Sorting using comparator
        Collections.sort(workers,workerComparator);
        List<Worker> workers1 = cr.createFullTimeWorker(fileName);
        // Sorting using comperable
        Collections.sort(workers1);

        //presentation of results
        for(int i = 0; i < workers.size(); i++) {
            System.out.println(workers.get(i).getSurname()+" "+workers.get(i).getName()+" "+workers.get(i).getCashPerHour());
        }
        System.out.println();
        System.out.print(" second list ");
        System.out.println();
        for(int i = 0; i < workers1.size(); i++) {
            System.out.println(workers1.get(i).getSurname()+" "+workers1.get(i).getName()+" "+workers1.get(i).getCashPerHour());
        }

    }
}