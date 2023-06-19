import java.util.Arrays;
import java.util.stream.Collectors;

public class Message {

    private int code = -1;
    private String[] body = null;

    private void codeParse(String code) {
        try {
            this.code = Integer.parseInt(code);
        } catch (NumberFormatException e) {
            this.code = -1;
        }
    }

    public Message(String msg) {
        String[] result;
        if (msg != null && (result = msg.split("/")).length == 2) {
            codeParse(result[0]);
            this.body = result[1].split(";");
        }
    }

    public int getCode() {
        return code;
    }

    public String[] getBody() {
        return body;
    }

    public boolean isValid() {
        return (code >= 0 && body != null && body.length > 0);
    }

    @Override
    public String toString() {
        return code + "/" + Arrays.stream(body).map(str -> str.concat(";")).collect(Collectors.joining());
    }
}
