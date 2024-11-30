import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private static final String EXIT = "CLOSE";

    Socket socket;

    //ta 2 Objects:
    ObjectInputStream serverInputStream;
    ObjectOutputStream serverOutputStream;

    public ServerThread(Socket datasocket) throws IOException {
        this.socket = datasocket;
        serverInputStream = new
                ObjectInputStream(socket.getInputStream());
        serverOutputStream = new
                ObjectOutputStream(socket.getOutputStream());

    }

    public void run() {

        try {
            ServerProtocol app = new ServerProtocol();

            Request req = (Request) serverInputStream.readObject();
            Reply rep = app.processRequest(req);


            while(!rep.getOpcode().equals("ERR")) {
                serverOutputStream.writeObject(rep);

                //prepei na ksana diavasoume ara:
                 req = (Request) serverInputStream.readObject();
                 rep = app.processRequest(req);

            }

            serverInputStream.close();
            serverOutputStream.close();

            socket.close();
            System.out.println("Data socket closed");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
