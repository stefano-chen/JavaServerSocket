import java.io.IOException;
import java.util.stream.Collectors;

public class Main {

    /* Create a TCP Server that allow the client to do the following actions.
     * the client have to send a packet with two fields:
     *   - the type of operation
     *   - the input of the operation
     * the server will apply the requested operation to the input and return a response with:
     *   - a numeric code that represent the request's status
     *   - the output of the operation
     *
     * the possible operations are:
     *   - All possible Permutation of a String
     *   - The number of words in a Phrase
     *   - The Uppercase of a String
     *   - Remove all space from a String
     * */
    public static void main(String[] args) {
        try {
            Server server = new Server(1234);
            server.addOperation(new Permutation())
                    .addOperation((String str) -> {
                        String[] strings = str.split(" ");
                        return strings.length;
                    })
                    .addOperation((String str) -> str.toUpperCase())
                    .addOperation((String str) ->
                            str.chars()
                                    .mapToObj((c) -> (char) c)
                                    .filter(c -> c != ' ')
                                    .map(c -> c.toString())
                                    .collect(Collectors.joining())
                    );
            server.start();
        } catch (IOException e) {
            System.out.println("Cannot Create/Run the Server");
        }
    }
}
