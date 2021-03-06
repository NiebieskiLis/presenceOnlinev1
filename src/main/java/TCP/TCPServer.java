package TCP;
import database.CreateData;
import workers.CreateWorker;
import database.Worker;
import database.WorkersList;
import java.io.*;
import java.net.*;
import java.util.List;

/**
 * A class that is used as TCP Server which is going to get workers list and deserialize it
 * @author Aleksandra Rezetka
 * @version 24/04/20
 */
public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectInputStream in;

    /**
     * A class that starts server
     * @param port it gets a port on which server is going to operate
     */
    public void start(int port) {
        try {
            //Server opens one previously specified port
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            try {
                //Here I open a new import stream with clientSocket
                in = new ObjectInputStream(clientSocket.getInputStream());
                WorkersList workers = (WorkersList) in.readObject();
                for(Worker w : workers.getWorkers()){
                    System.out.print(w.toString()+"/n");
                }
                in.close();
            }catch (IOException | ClassNotFoundException f) {
                System.out.print("Sth is wrong with deserializaton");
            }
            clientSocket.close();
            serverSocket.close();
        }catch (IOException e){
            System.out.println("Unable to create socket");
        }
    }
    public static void main(String[] args){
//        CreateData cr = new CreateData();
//        String fileName = "Data\\FT1.json";
//        List<Worker> workers = cr.createFullTimeWorker(fileName);
//        WorkersList w = new WorkersList();
//        w.setWorkers(workers);
//        TCPServer server = new TCPServer();
//        server.start(6666);
//        System.out.print("Server was created");
    }
}
