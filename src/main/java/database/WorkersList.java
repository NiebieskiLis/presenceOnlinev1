package database;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static java.nio.channels.Channels.newInputStream;
import static java.nio.channels.Channels.newOutputStream;

/**
 * @version 17/04/2020/v1
 * @author Aleksandra Rezetka
 * a class that enables the synchronized acess for therad to add workers to the list they share
 */
public class WorkersList implements Serializable {
    List<database.Worker> workers;

    public WorkersList() {
        this.workers = new ArrayList<>() ;
    }


    public List<database.Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<database.Worker> workers) {
        this.workers = workers;
    }


    /**
     *A method that adds element to the shared list it is SYNCHRONIZED (because it's a critical part of code)
     * @param element takes a worker that is going to be added to
     */
    public synchronized void addToList(database.Worker element){
        workers.add(element);
    }

    /**
     * A method that enables serialization full workers list in company
     * @param filename - a path where to serialize object
     */

    public void Serialization(String filename){
        File file = new File(filename);
        try (FileChannel fch = FileChannel.open((new File(filename)).toPath() , StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            FileLock lock=fch.tryLock();
            if(lock!=null){
                try ( OutputStream in = newOutputStream(fch);ObjectOutputStream outputStream =new ObjectOutputStream(in)){
                    outputStream.writeObject(this);
                    System.out.print("Object was serialized \n");
                    try {
                        System.out.print(" Now in thread");
                        Thread.sleep(4000);
                        System.out.print("\n Sleep finished \n");
                    } catch (InterruptedException e) {
                        System.out.print("Can't sleep 2night");
                    }
                }catch (IOException e){
                    System.out.print("Sth is wrong with input file \n");
                }
            }else System.out.println("\n Couldn't get into file and save it :<\n");

        } catch (IOException e) {
            System.out.print("There is trouble in opening file !\n");
        }
    }

    /**
     * Method that implements deserialization of full workers list
     * @param filename - a path to serialized object
     * @return a deserialized object
     */

    public WorkersList Deserialization(String filename){
        WorkersList object1 = null;
        File file = new File(filename);
        try (FileChannel fch = FileChannel.open((new File(filename)).toPath() , StandardOpenOption.READ, StandardOpenOption.WRITE)) {
            FileLock lock=fch.tryLock();
            System.out.print("Now in the lock \n");
            if(lock!=null){
                try ( InputStream in = newInputStream(fch);ObjectInputStream inputStream =new ObjectInputStream(in)){
                    object1 = (WorkersList) inputStream.readObject();
                    System.out.print("Object was deserialized \n ");

                }catch (IOException|ClassNotFoundException e){
                    System.out.print("Sth is wrong with input file \n");
                }
            }else System.out.println("\n Couldn't get into file and save it :<");

        } catch (IOException e) {
            System.out.print("There is trouble in opening file ! \n ");
        }
        return object1;
    }
}
