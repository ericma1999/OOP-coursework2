import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JSONReader {

    private static ArrayList<Character> skipKeys = new ArrayList<>(Arrays.asList('[', ']','"',' ', ',', '\n'));
    private HashMap<String, String> testHolder = new HashMap<>();


    public JSONReader(String path) throws IOException{
        FileReader file = new FileReader(path);

        try (BufferedReader contents = new BufferedReader(file)) {
            int charIntRepresentation;
            while ((charIntRepresentation = contents.read()) != -1){
                Character character = Character.toChars(charIntRepresentation)[0];

                if (skipKeys.contains(character)){
                    continue;
                }

                if (character.equals('{')){
                    this.readProperties(contents);
                }
            }




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readProperties(BufferedReader contents) throws Exception {
        int charIntRepresentation;
        ArrayList<Character> stack = new ArrayList<>();
        stack.add('{');
        StringBuilder currentContent = new StringBuilder();
        StringBuilder currentProperty = new StringBuilder();
        while ((charIntRepresentation = contents.read()) != -1){
            Character character = Character.toChars(charIntRepresentation)[0];


            if ((character.equals(' ') || character.equals('\n')) && stack.get(stack.size() - 1) != '"'){
                continue;
            }


            if (character.equals('"')){

                if (stack.get(stack.size() -1) != '"'){
                    stack.add('"');
                }else {
                    stack.remove(stack.size() - 1);
                    Character nextCharacter = readTillNoWhiteSpace(contents);

                    System.out.println("next character" + nextCharacter);

                    if (nextCharacter.equals(':')){

                        this.testHolder.put(currentContent.toString(), "");
                        System.out.println("Adding key: " + currentContent.toString());
                        currentProperty = new StringBuilder(currentContent);
                        currentContent = new StringBuilder();

                    }else if (nextCharacter.equals(',')){
                        if (!this.testHolder.get(currentProperty.toString()).equals("")){
                            throw new Exception("FOrmat was incorrect");
                        }else {
                            this.testHolder.put(currentProperty.toString(), currentContent.toString());
                            System.out.println("Adding value: " + currentContent.toString());
                            currentContent = new StringBuilder();
                            currentProperty = new StringBuilder();
                        }
                    }else if(nextCharacter.equals('}') && stack.get(stack.size() -1) != '"'){
                        System.out.println("Adding value: " + currentContent.toString());
                        this.testHolder.put(currentProperty.toString(), currentContent.toString());
                        stack.remove(stack.size() - 1);
                        if (stack.isEmpty()){
                            System.out.println("breaking");
                            System.out.println("\n\n\n");
                            break;
                        }
                    }

                }
                continue;
            }


            currentContent.append(character);

        }
    }


    private Character readTillNoWhiteSpace(BufferedReader contents) throws Exception{
        Character character;
        while (true){
            character = Character.toChars(contents.read())[0];

            if (!(character.equals('\n') || character.equals(' '))) {
                return character;
            }
        }
    }
}
