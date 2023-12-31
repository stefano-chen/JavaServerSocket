import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandle implements Runnable {

    private final Socket client;

    private final String QUIT;
    private final BufferedWriter output;
    private final BufferedReader input;

    private List<Operation> operations;

    public ClientHandle(final Socket client, final String quit_command, List<Operation> opList) throws IOException {
        this.client = client;
        this.QUIT = quit_command;
        this.operations = opList;
        this.output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }

    private int isNumeric(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void run() {
        try (output; input) {
            String str;
            Message msg;
            output.write("200/Connected" + System.lineSeparator());
            output.flush();
            while (!(str = input.readLine()).equalsIgnoreCase(QUIT)) {
                msg = new Message(str);
                if (!msg.isValid() || msg.getCode() >= operations.size()) {
                    output.write("400/Malformed Request" + System.lineSeparator());
                    output.flush();
                    continue;
                }
                Operation op = operations.get(msg.getCode());
                String resp = "200/";
                for (String i : msg.getBody()) {
                    resp += op.process(i) + ";";
                }
                output.write(resp + System.lineSeparator());
                output.flush();
            }
        } catch (IOException e) {
            System.out.println("Communication Exception");
        } finally {
            System.out.println("[Disconnected] " + client.getInetAddress().getHostAddress());
        }
    }
}
