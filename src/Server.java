import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static int PORT;
    private static String QUIT_COMMAND = "QUIT";
    private ServerSocket server;
    private List<Operation> operations;

    public Server(int port) throws IOException {
        this.PORT = port;
        server = new ServerSocket(PORT);
        operations = new ArrayList<Operation>();
    }

    public Server addOperation(Operation<?, ?> op) {
        operations.add(op);
        return this;
    }

    public void start() {
        try {
            System.out.println("Server running on " + InetAddress.getLocalHost().getHostAddress() + ":" + PORT);
        } catch (UnknownHostException e) {
            System.out.println("Unknown Local Host ");
        }
        while (true) {
            try {
                Socket client = server.accept();
                new Thread(new ClientHandle(client, QUIT_COMMAND, operations)).start();
                System.out.println("[Connected] " + client.getInetAddress().getHostAddress());
            } catch (IOException e) {
                System.out.println("Connection Exception");
            }
        }
    }
}
