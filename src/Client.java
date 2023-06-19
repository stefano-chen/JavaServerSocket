import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("192.168.1.1", 1234);
             BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
            String str;
            Message msg;
            while (true) {
                str = in.readLine();
                msg = new Message(str);
                //String[] resp = str.split("/");
                System.out.println("Status Code: " + msg.getCode());
                //String[] results = resp[1].split(";");
                Arrays.stream(msg.getBody()).forEach((line) -> System.out.println(line));
                System.out.print("Command: ");
                System.out.flush();
                str = user.readLine();
                out.write(str + System.lineSeparator());
                out.flush();
                if (str.equalsIgnoreCase("QUIT"))
                    return;
            }
        } catch (IOException e) {
            System.out.println("Connection Failed");
        }
    }
}
