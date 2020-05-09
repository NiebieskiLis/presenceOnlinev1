package database;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import workers.FullTimeWorker;
import workers.PartTimeWorker;
import workers.Worker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A class that generate workers from JSON file generated from mockaroo.com
 */
public class CreateData  {

    /**
     * This method creates a list of full-time workers from the json file while using JSONPARSER and JSONObject
     * @param filename it takes path to the json file with data
     * @return a list of FullTimeWorkers
     */
    public List<Worker> createFullTimeWorker(String filename){
        String line = null;
        String password;
        JSONObject obj;
        List<Worker> lista = new ArrayList<>();
        ReadWriteLock myLock = new ReentrantReadWriteLock();
        myLock.readLock().lock();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            while((line = br.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                String name = (String) obj.get("name");
                String surname = (String) obj.get("surname");password = (String) obj.get("password");

                int paidLeave = Integer.valueOf(obj.get("paidLeave").toString());
                int childCareLeave = Integer.valueOf(obj.get("childCareLeave").toString());
                boolean multisport = ((boolean) obj.get("multisport"));
                boolean healthcare = ((boolean) obj.get("healtcareLeave"));
                int cashPerHour = Integer.valueOf(obj.get("cashPerHour").toString());
                lista.add(new FullTimeWorker(password ,name ,surname,cashPerHour,paidLeave,multisport,healthcare,childCareLeave));
            }
        }catch(FileNotFoundException f)
        {
            f.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }finally {
            myLock.readLock().unlock();
        }

        return lista;
    }
    /**
     * This method creates a list of part-time workers
     * @param filename it takes path to the json file with data
     * @return a list of PartTimeWorkers
     */
    public List<Worker> createPartTimeWorker(String filename , List<Worker> fullTime){
        String line = null;
        String password;
        JSONObject obj;
        List<Worker> lista = new ArrayList<>();
        ReadWriteLock myLock = new ReentrantReadWriteLock();
        myLock.readLock().lock();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            while((line = br.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                String name = (String) obj.get("name");
                String surname = (String) obj.get("surname");
                password = (String) obj.get("password");
                int cash = Integer.valueOf(obj.get("cashPerHour").toString());

                int min = Integer.valueOf(obj.get("minNumberOfHours").toString());
                //PartTimeWorker.Department department = ((PartTimeWorker.Department) obj.get("department"));
                Worker supervisor = fullTime.get(Integer.valueOf(obj.get("supervisorID").toString()));
                int year = Integer.valueOf(obj.get("year").toString());
                int month = Integer.valueOf(obj.get("month").toString());
                int day = Integer.valueOf(obj.get("day").toString());
                //I just used IT Department
                workers.PartTimeWorker.Department department = workers.PartTimeWorker.Department.IT;

                lista.add(new PartTimeWorker(password, name, surname, cash, min, department, supervisor, year, month, day));
            }
        }catch(FileNotFoundException f)
        {
            f.printStackTrace();
        } catch(ParseException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }finally {
            myLock.readLock().unlock();
        }

        return lista;
    }
}
