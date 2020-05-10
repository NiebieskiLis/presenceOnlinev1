package TCP;
import workers.CreateWorker;
import workers.Worker;
import database.WorkersList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @author Maciej Adamczyk
 * @version 25.04.2020
 * TCP Client class, which provides features and TCP connection from a client level
 */
public class TCPClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    /**
     * Method which starts TCP connection, sends a serialized object to a server
     * @param ip
     * @param port
     * @param w
     */
    public void start(String ip, int port , WorkersList w) {
        try {
            clientSocket = new Socket(ip, port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(w);
            System.out.print("Object was send to server \n");
            out.close();

        } catch (IOException e) {
            System.out.print("Unable to open sockets");
        }
    }

    /**
     * Method in which one Worker object is defined to send to the server
     * The method start os also used in it to send and object to server
     * @param args
     */
    public static void main(String[] args){
//        CreateWorker cr = new CreateWorker();
//        String fileName = "Data\\FT1.json";
//
//        List<Worker> workers = cr.createFullTimeWorker(fileName);
//        WorkersList w = new WorkersList();
//        w.setWorkers(workers);
//        System.out.print("Client was created \n");
//        TCPClient client = new TCPClient();
//        client.start("127.0.0.1", 6666 ,w);
    }
}




