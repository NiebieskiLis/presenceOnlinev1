package test;


import java.util.ArrayList;
import java.util.List;
import workers.*;
import thread.*;

/**
 * This class represents worker shift
 * it has a data about the start and end time as well as sum of hours spend in work
 * @version 05/04/2020/v1
 *  @author Aleksandra Rezetka
 */

public class TestThread extends Thread{
    public WorkersList WBL = new WorkersList();
    /**
     *     this is a main that allows me to check how
     *     this thread i  s going to look for workers
     *     from specific departments and then adds them to the list
     */

    public static void main(String[] args)
    {
        //a path to the Jsons files with data
        String fileName = "Data\\FT1.json";
        String fileName2 = "Data\\FT2.json";
        String fileName3 = "Data\\FT3.json";
        String fileName4 = "Data\\FT4.json";
        String fileName5 = "Data\\FT5.json";
        String fileName6 = "Data\\PT1.json";
        String fileName7 = "Data\\PT2.json";
        String fileName8 = "Data\\PT3.json";
        String fileName9 = "Data\\PT4.json";
        String fileName10 = "Data\\PT5.json";

        //Creating an insatnce of CreateWorkers that helps me with bulk creation of workers
        CreateWorker cr = new CreateWorker();
        List<Worker> workers = cr.createFullTimeWorker(fileName);
        //This loop is made just and only to create almost 3 mln of workers to test thread
        for (int i = 0 ; i < 300 ; i++) {
            workers.addAll(cr.createFullTimeWorker(fileName2));
            workers.addAll(cr.createFullTimeWorker(fileName3));
            workers.addAll(cr.createFullTimeWorker(fileName4));
            workers.addAll(cr.createFullTimeWorker(fileName5));
            workers.addAll(cr.createPartTimeWorker(fileName6, workers));
            workers.addAll(cr.createPartTimeWorker(fileName7, workers));
            workers.addAll(cr.createPartTimeWorker(fileName8, workers));
            workers.addAll(cr.createPartTimeWorker(fileName9, workers));
            workers.addAll(cr.createPartTimeWorker(fileName10, workers));


            workers.addAll(cr.createFullTimeWorker(fileName));
        }
        //sekwencyjne
        List<Worker> WBL = new ArrayList<>();
        long start2 = System.currentTimeMillis();

        for(int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getCashPerHour() < 20) {
                WBL.add(workers.get(i));
                System.out.print(workers.get(i).toString());
            }
        }
        long end2 = System.currentTimeMillis();
        long duration2 = end2 - start2;


// 4 watki
        WorkersList w = new WorkersList();
        int size = workers.size();
        int threadNO =4;
        int part = size/threadNO;
        List<Worker> a = workers.subList(0, part-5000);
        List<Worker> b = workers.subList(part-5000,2*part-5000 );
        List<Worker> c = workers.subList(2*part-5000,3*part-5000 );
        List<Worker> d = workers.subList(3*part-5000,size );

        Thread t1 = new Thread(new Thread1(a, 20, w));
        Thread t2 = new Thread(new Thread1(b, 20, w));
        Thread t3 = new Thread(new Thread1(c, 20, w));
        Thread t4 = new Thread(new Thread1(d, 20, w));
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();

        } catch (InterruptedException error) {
            error.printStackTrace();
        }
        long end = System.currentTimeMillis();
        long duration = end - start;

        System.out.println();
////3 watki
//        WorkersList w1 = new WorkersList();
//        int size1 = workers.size();
//        int threadNO1 =3;
//        int part1 = size/threadNO1;
//        List<Worker> a1 = workers.subList(0, part1-5000);
//        List<Worker> b1 = workers.subList(part1-5000,2*part1-5000 );
//        List<Worker> c1 = workers.subList(2*part1-5000,size1 );
//
//        Thread t11 = new Thread(new Thread1(a1, 20, w1));
//        Thread t12 = new Thread(new Thread1(b1, 20, w1));
//        Thread t13 = new Thread(new Thread1(c1, 20, w1));
//
//        long start1 = System.currentTimeMillis();
//        t11.start();
//        t12.start();
//        t13.start();
//        try {
//            t11.join();
//            t12.join();
//            t13.join();
//
//        } catch (InterruptedException error) {
//            error.printStackTrace();
//        }
//        long end1 = System.currentTimeMillis();
//        long duration1 = end1 - start1;
//
////2 watki
//        WorkersList w2 = new WorkersList();
//        int size2 = workers.size();
//        int threadNO2 =2;
//        int part2 = size/threadNO2;
//        List<Worker> a2 = workers.subList(0, part2-5000);
//        List<Worker> b2 = workers.subList(part2-5000,size2 );
//
//        Thread t21 = new Thread(new Thread1(a2, 20, w2));
//        Thread t22 = new Thread(new Thread1(b2, 20, w2));
//
//        long start3 = System.currentTimeMillis();
//        t21.start();
//        t22.start();
//        try {
//            t21.join();
//            t22.join();
//
//        } catch (InterruptedException error) {
//            error.printStackTrace();
//        }
//        long end3 = System.currentTimeMillis();
//        long duration3 = end3 - start3;

        System.out.println();
        //System.out.println("Number of threads " +threadNO );
        System.out.println("List underpaid workers (in total : " + w.getWorkers().size() + " )");
        System.out.println("Duration time "+threadNO+" : " +duration);
        //   System.out.println("Duration time of "+threadNO1+" : " +duration1);
        // System.out.println("Duration time of 2 watki  " +duration3);
        System.out.println("Duration time of seq  " +duration2);
        System.out.println("Full number of workers : "+workers.size());
    }
}

