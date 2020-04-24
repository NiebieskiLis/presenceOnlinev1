package test;

import workers.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import static java.nio.channels.Channels.newOutputStream;


/**
 * @version 17/04/2020/v1
 * @author Aleksandra Rezetka
 *This Class is representation of prototype fo Accounting Platform where Accounting can take a look to the workers lists
 */
public class AccountantPlatform {
    /**
     * An empty constructor that is made for test purposes
     */
    public AccountantPlatform() {
    }

    /**
     *This method is used during save to the file by binary method
     * I also used this method to confim that my lock on file works
     * I did it as follows :
     * Opened 2 instances of my program and at once tried to lunch this method
     * it turns out that only one process can do this
     * and others in this time are unable to reach file
     * @param workers is a list of workers that an accountant wants to save
     * @param path is a path where we are going to store the file
     */
    public void binarySave(WorkersList workers , String path) {
        System.out.print(LocalDateTime.now() + "\n");

        try (FileChannel fch = FileChannel.open((new File(path)).toPath() ,StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            try(FileLock lock=fch.tryLock()) {
                System.out.print("Now in the lock");
                if(lock!=null){
                    OutputStream outputStream =newOutputStream(fch);
                    for (Worker w : workers.getWorkers())
                        outputStream.write((w.getName() + ";" + w.getSurname() + ";" + w.getCashPerHour() + ";" + w.getPassword() + "\n").getBytes());
                    try {
                        System.out.print("\n Now in thread");
                        Thread.sleep(4000);
                        System.out.print("\n Sleep finished \n");
                        System.out.print(LocalDateTime.now() + "\n");

                    } catch (InterruptedException e) {
                        System.out.print("Can't sleep 2night");
                    }
                    outputStream.close();

                }else System.out.println("\n Couldn't get into file and save it :< Someone is already using this file");
            }
        } catch (IOException e) {
            System.out.print("There is trouble in opening file !");
        }
        System.out.print("Binary file was saved  ! \n");
    }

    /**
     * This method enabes user to load from binary file a workers list
     * @param path a path to the file from which we try to load list if it's not a binary one exception will be handled!
     * @return returns loaded workers list
     */
    public WorkersList binaryRead(String path) {
        WorkersList object1= new WorkersList();
        try (RandomAccessFile reader = new RandomAccessFile(path, "rw")){
            FileChannel fch = reader.getChannel();
            try(FileLock lock = fch.tryLock())
            {
                if (lock != null) {
                    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                        int bufferSize = 1024;
                        if (bufferSize > fch.size()) {
                            bufferSize = (int) fch.size();
                        }
                        ByteBuffer buff = ByteBuffer.allocate(bufferSize);

                        while (fch.read(buff) > 0) {
                            out.write(buff.array(), 0, buff.position());
                            buff.clear();
                        }

                        String s = new String(out.toByteArray());
                        String[] allList = s.split("\n");
                        for (String line : allList) {
                            String[] pola = line.split(";");
                            if (line.contains(";")) {
                                object1.addToList(new FullTimeWorker(pola[3], pola[0], pola[1], Integer.parseInt(pola[2]), 0, true, true, 0));
                            }
                        }
                        try {
                            System.out.print(" Now in thread");
                            Thread.sleep(4000);
                            System.out.print("\n Sleep finished \n");
                        } catch (InterruptedException e) {
                            System.out.print("Can't sleep 2night");
                        }
                    } catch (IOException | ArrayIndexOutOfBoundsException e) {
                        System.out.print("It wasn't proper file with binary text! Better luck next time :) \n");
                    }
                }else System.out.println("\n Couldn't get into file and save it :< Someone is already using this file");
            }

        }catch (FileNotFoundException f){
            System.out.print("Couldn't find file");
        } catch (IOException e)
        {
            System.out.print("Unable to open file");
        }
        return object1;
    }
    /**
     *This method is used during save to the file by plain text method
     * @param workers is a list of workers that an accountant wants to save
     * @param path is a path where we are going to store the file
     */

    public void normalSave(WorkersList workers , String path) {
        File file = new File(path);
        //Opening channel and creating locks
        try (FileChannel fch = FileChannel.open(file.toPath() , StandardOpenOption.CREATE, StandardOpenOption.WRITE);FileLock lock=fch.tryLock()) {
            System.out.print("Now in the lock \n");
            if(lock!=null){
                //getting into the file and saving it !
                try ( OutputStream out = newOutputStream(fch)){
                    PrintWriter writer = new PrintWriter(out);
                    for(Worker w : workers.getWorkers()){
                        writer.write(w.getName() + ";" + w.getSurname() + ";" + w.getCashPerHour() + ";" + w.getPassword() + "\n");
                        System.out.print("File was save to the : " + path +"\n");
                        System.out.print("In Testing Branch 14.49 : " + path +"\n");

                    }
                    writer.close();
                }
            }else System.out.println("\n Couldn't get into file and save it :<");

        } catch (IOException e) {
            System.out.print("There is trouble in opening file !");
        }
    }

    /**
     * A method that is a part of menu for saving type - it enables user to choose how he wants to save their file
     * There are three methods available - binary save , plain text , serialization
     * @param path - is a path where we are going to store the file
     * @param workers a list that is going to be saved
     */
    public void saveType(String path, WorkersList workers ){
        boolean flag = true;

        while (flag) {
            System.out.print("How do you want to save your file? \n b - binary \n n - normal text \n x - serialize object \n !!!! WARNING !!!! - NORMAL TEST HOLDS ALL THE ATTRIBUTES ABOUT WORKERS SEPARATED WITH ; \n");
            String save_type = "";
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                save_type = obj.readLine();
            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
            if (save_type.trim().toLowerCase().equals("b")) {
                binarySave(workers, path);
                flag = false;
            } else if (save_type.trim().toLowerCase().equals("n")) {
                normalSave(workers, path);
                flag = false;
            } else if (save_type.trim().toLowerCase().equals("x")) {
                workers.Serialization(path);
                flag = false;
            } else {
                System.out.print("Try once more !");
            }
        }
    }

    /**
     * This method enables creation of the path of the file where we want to  save our list
     * @param file_type - there are two types available s - for the save to existing file option n - for the creation if non of both - program goes back to main menu
     * @param file - a directory where we are going to save file
     * @return - a String path were we are going to save file
     */
    public String createPath( String file_type,  File file)   {
        String path = "";
        boolean flag = true;
        if (file_type.trim().toLowerCase().equals("s")) {
            //jeśli dodajemy do tych co już są
            File[] files = file.listFiles();
            //na to poleci for z wypisywaniem elementu
            int elem = 1;
            for (File f : files) {
                System.out.print("\n" + elem + "\t" + f.toString() + "\n");
                elem++;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Which one you choose ? \n Remember all your previous data is going to be removed ");
            try {
                elem = Integer.parseInt(reader.readLine()) - 1;
            } catch (IOException e){
                System.out.print("Couldn't read line \n");
            }
            path = files[elem].toString();
            //uzytkownik wybiera jak chce zapisac plik
        } else if (file_type.trim().toLowerCase().equals("n")) {
            String file_name = "";
            System.out.print("please enter file name with extension (like .txt)");

            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                file_name = obj.readLine();
            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
            File myObj = null;
            path = file + "\\" + file_name;
            myObj = new File(path);
            try {
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            }catch (IOException e){
                System.out.print("Unable to create file in this path");
            }
        }
        return path;
    }

    /**
     *This method enables to User to read a list from a given filepath
     * @param path a path where the file is stored
     * @return a new list of workers created from the file
     */

    public WorkersList normalRead(String path) {
        WorkersList object1= new WorkersList();
        try (RandomAccessFile reader = new RandomAccessFile(path, "rw")){
            FileChannel fch = reader.getChannel();
            FileLock lock = fch.tryLock();
            try (BufferedReader br = new BufferedReader(Channels.newReader(fch , "UTF-8")))
            {
                if(lock != null){
                    String line ;
                    while ((line = br.readLine())!=null){
                        String[] pola = line.split(";");
                        if (line.contains(";") & pola.length>3) {
                            object1.addToList(new FullTimeWorker(pola[3], pola[0], pola[1], Integer.parseInt(pola[2]), 0, true, true, 0));
                        }

                    }
                    try {
                        System.out.print(" Now in thread");
                        Thread.sleep(4000);
                        System.out.print("\n Sleep finished \n");
                    } catch (InterruptedException e) {
                        System.out.print("Can't sleep 2night");
                    }
                }else System.out.println("\n Couldn't get into file and save it :< Someone is already using this file");
            }

        }catch (FileNotFoundException f){
            System.out.print("Couldn't find file");
        } catch (IOException e)
        {
            System.out.print("Unable to open file");
        }
        return object1;
    }

    /**
     * A method that reads from simple file and then creates a list of workers
     * @return returns WorkerList read from file
     */
    public WorkersList readFromFile() {
        WorkersList workers = new WorkersList();
        boolean flag = true;
        boolean flag2 = true;
        while(flag) {
            System.out.print("Where is you list saved ? (please enter full file path) \n");
            String path = "";
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                path = obj.readLine();
            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
            File file = new File(path);
            if (file.isFile()) {
                flag = false;
                while(flag2) {
                    String file_type = "";
                    System.out.print("what kind of file is it? \n b - binary \n n - normal text \n x - serialize object \n");

                    try {
                        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                        file_type = obj.readLine();
                    } catch (IOException e) {
                        System.out.print("Unable to read file type");
                    }
                    switch (file_type){
                        case "e":
                            workers = binaryRead(path);
                            flag2 =false;
                        case "n" :
                            workers = normalRead(path);
                            flag2 =false;
                        case "x" :
                            workers.Deserialization(path);
                            flag2 =false;
                    }
                }
            } else System.out.print("Given Path is not a file! \n");
        }
        return workers;
    }

    /**
     * Menu for file saving
     * @param workers takes a list that needs to be saved
     */
    public void SaveToFile(WorkersList workers) {
        boolean flag = true;
        boolean flag2 = true;
        while(flag) {
            System.out.print("Where do you want to save your file (please enter full directory path) \n");
            String path = "";
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                path = obj.readLine();
            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }

            File file = new File(path);

            //jeżeli ścieżka do directory to wypisz pliki
            if (file.isDirectory()) {
                flag = false;
                String file_type = "";
                while (flag2) {
                    System.out.print("Do you want to create a new file or  save to existing one \n n - create new file  \n s - save to exisitng one");
                    try {
                        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                        file_type = obj.readLine();
                    } catch (IOException e) {
                        System.out.print("Unable to read character from file_type");
                    }
                    if (file_type.trim().toLowerCase().equals("s") || file_type.trim().toLowerCase().equals("n")) {
                        path = createPath(file_type, file);
                        saveType(path, workers);
                        flag2 = false;
                    } else {
                        System.out.print("I don't understand you");
                    }
                }
            } else System.out.print("Given Path is not a directory !");
        }
    }

    /**
     * Main method which purpose is to test interface of Accounting Platform
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "Data\\FT1.json";
        CreateWorker cr = new CreateWorker();
        boolean flag = true;
        WorkersList workers = new WorkersList();
        workers.setWorkers(cr.createFullTimeWorker(fileName));
        AccountantPlatform test = new AccountantPlatform();
        //A main menu made in a loop
        while (flag){
            System.out.print("What would you like to do? \n s - save list  \n l - load list \n e - exit \n ");
            String element = "";
            try {
                BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
                element = obj.readLine();
            } catch (IOException e) {
                System.out.print("Unable to read character from file_type");
            }
            if ("e".equals(element)){
                flag = false;
            }else if ("s".equals(element))
            {
                test.SaveToFile(workers);
            }
            else if ("l".equals(element))
            {
                workers = test.readFromFile();
            }
            else {
                System.out.print("I don't understand your command. Try again \n");
            }
        }
        //Here I can check wheather two files are the same!
//        workers.Serialization("Data\\file.ser");
//        WorkersList o1 = new WorkersList();
//        o1 = o1.Deserialization("Data\\file.ser");
////Tu sprawdzam czy obiekty są takie same
//       for( int i = 0 ; i< o1.getWorkers().size();i++){
//           if (o1.getWorkers().get(i).compareTo(workers.getWorkers().get(i))!=0) {
//               System.out.print("Jakiś element się nie zgadza");
//           }
//       }
        //

    }
}
