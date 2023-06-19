import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Permutation implements Operation<String, String> {

    List<String> permutations = new ArrayList<>();

    private void permute(String result, String letters) {
        if (letters.length() == 0) {
            this.permutations.add(result);
            return;
        }
        for (int i = 0; i < letters.length(); i++) {
            String str = result + letters.charAt(i);
            permute(str, letters.substring(0, i) + letters.substring(i + 1));
        }
    }

    @Override
    public String process(String input) {
        permutations.clear();
        permute("", input);
        return permutations.stream().map(str -> str + " ").collect(Collectors.joining());
    }

}
