import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JSONReader {

    private static ArrayList<Character> skipKeys = new ArrayList<>(Arrays.asList('[', ']','"',' '));
    private HashMap<String, String> testHolder = new HashMap<>();
    private StringBuilder currentContent = new StringBuilder();


    public JSONReader(String path) throws IOException{
        FileReader file = new FileReader(path);

        try (BufferedReader contents = new BufferedReader(file)) {
            int charIntRepresentation;
            while ((charIntRepresentation = contents.read()) != -1){
                Character character = Character.toChars(charIntRepresentation)[0];

                if (skipKeys.contains(character)){
                    continue;
                }

                System.out.println(character);
                currentContent.append(character);
            }

            System.out.println(currentContent.toString());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
