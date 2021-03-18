import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class JSONReader {

    private final HashMap<String, String> testHolder = new HashMap<>();
    private final ArrayList<Character> stack = new ArrayList<>();
    private StringBuilder currentContent = new StringBuilder();
    private StringBuilder currentProperty = new StringBuilder();
    private static final Pattern pattern = Pattern.compile("[\\[\\]\", ]");


    public JSONReader(String path) throws IOException{
        FileReader file = new FileReader(path);

        try (BufferedReader contents = new BufferedReader(file)) {
            int charIntRepresentation;
            while ((charIntRepresentation = contents.read()) != -1){
                Character character = Character.toChars(charIntRepresentation)[0];

                if (pattern.matcher(character.toString()).find()){
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
        stack.add('{');
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
                    if(!handlePropertyEndCases(nextCharacter)){
                        break;
                    }
                }
                continue;
            }
            currentContent.append(character);

        }
    }

    private void handleColon(){
        this.testHolder.put(currentContent.toString(), "");
        System.out.println("Adding key: " + currentContent.toString());
        currentProperty = new StringBuilder(currentContent);
        currentContent = new StringBuilder();
    }

    private boolean handleComma(){
        if (!this.testHolder.get(currentProperty.toString()).equals("")){
            return false;
        }else {
            this.testHolder.put(currentProperty.toString(), currentContent.toString());
            System.out.println("Adding value: " + currentContent.toString());
            currentContent = new StringBuilder();
            currentProperty = new StringBuilder();
        }
        return true;
    }

    private boolean handleCloseBracket(){
        System.out.println("Adding value: " + currentContent.toString());
        this.testHolder.put(currentProperty.toString(), currentContent.toString());
        stack.remove(stack.size() - 1);
        if (stack.isEmpty()){
            System.out.println("\n\n\n");
            return false;
        }
        return true;
    }

    private boolean handlePropertyEndCases(Character nextCharacter) throws Exception{
        switch (nextCharacter){
            case ':':
                handleColon();
                break;
            case ',':
                if (!handleComma()){
                    throw new Exception("Format was incorrect");
                }
                break;
            case '}':
                return handleCloseBracket();
            default:
                throw new Exception("Something is wrong");
        }
        return true;
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
