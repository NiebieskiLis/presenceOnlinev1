package TCP;
import workers.CreateWorker;
import workers.Worker;
import workers.WorkersList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class TCPClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

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
    public static void main(String[] args){
        CreateWorker cr = new CreateWorker();
        String fileName = "Data\\FT1.json";

        List<Worker> workers = cr.createFullTimeWorker(fileName);
        WorkersList w = new WorkersList();
        w.setWorkers(workers);
        System.out.print("Client was created \n");
        TCPClient client = new TCPClient();
        client.start("127.0.0.1", 6666 ,w);
    }
}




